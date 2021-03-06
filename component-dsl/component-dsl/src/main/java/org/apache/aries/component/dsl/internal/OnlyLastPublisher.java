/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.aries.component.dsl.internal;

import org.apache.aries.component.dsl.OSGi;
import org.apache.aries.component.dsl.Publisher;
import org.apache.aries.component.dsl.OSGiResult;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * @author Carlos Sierra Andrés
 */
public class OnlyLastPublisher<T> implements Publisher<T> {

    public OnlyLastPublisher(Publisher<? super T> op) {
       this(op, null);
    }

    public OnlyLastPublisher(
        Publisher<? super T> op, Supplier<T> injectOnLeave) {

        _op = op;
        _injectOnLeave = injectOnLeave;
        _terminator = OSGi.NOOP;
    }

    private final Publisher<? super T> _op;
    private AtomicLong _counter = new AtomicLong();
    private Supplier<T> _injectOnLeave;
    private Runnable _terminator;

    @Override
    public synchronized OSGiResult publish(T t) {
        _terminator.run();

        _terminator = _op.publish(t);

        if (_injectOnLeave == null) {
            return OSGi.NOOP;
        }
        else {
            _counter.incrementAndGet();

            return () -> {
                synchronized (this) {
                    _terminator.run();

                    if (_counter.decrementAndGet() > 0) {
                        _terminator = _op.publish(_injectOnLeave.get());
                    }
                }
            };
        }
    }

}
