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

package org.apache.aries.osgi.functional.internal;

import org.apache.aries.osgi.functional.OSGi;
import org.apache.aries.osgi.functional.OSGiResult;
import org.apache.aries.osgi.functional.Publisher;
import org.osgi.framework.BundleContext;

import java.io.Closeable;
import java.util.function.Function;

import static org.apache.aries.osgi.functional.OSGi.NOOP;

/**
 * @author Carlos Sierra Andrés
 */
public class Pad<T, S> implements Publisher<T>, Closeable {

    public Pad(
        BundleContext bundleContext,
        Function<OSGi<T>, OSGi<S>> fun,
        Publisher<? super S> continuation) {

        ProbeImpl<T> probe = new ProbeImpl<>();

        OSGiImpl<S> next = (OSGiImpl<S>) fun.apply(probe);

        _result = next.run(bundleContext, continuation);

        _publisher =
            probe.getPublisher() != null ?
                probe.getPublisher() :
                __ -> NOOP;
    }

    @Override
    public void close() {
        _result.close();
    }

    @Override
    public Runnable publish(T t) {
        return _publisher.publish(t);
    }

    private final OSGiResult _result;
    private final Publisher<? super T> _publisher;
}