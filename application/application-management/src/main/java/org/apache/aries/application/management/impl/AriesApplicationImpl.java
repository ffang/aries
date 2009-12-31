/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIESOR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.aries.application.management.impl;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import org.apache.aries.application.ApplicationMetadata;
import org.apache.aries.application.DeploymentMetadata;
import org.apache.aries.application.management.AriesApplication;
import org.apache.aries.application.management.BundleInfo;

public class AriesApplicationImpl implements AriesApplication {

  private Set<BundleInfo> _bundleInfo;
  private ApplicationMetadata _applicationMetadata;
  private DeploymentMetadata _deploymentMetadata;
  
  public AriesApplicationImpl(ApplicationMetadata meta, Set<BundleInfo> bundleInfo) {
    _applicationMetadata = meta;
    _bundleInfo = bundleInfo;
    _deploymentMetadata = null;
    
  }
  
  public ApplicationMetadata getApplicationMetadata() {
    return _applicationMetadata;
  }

  public Set<BundleInfo> getBundles() {
    return _bundleInfo;
  }

  public DeploymentMetadata getDeploymentMetadata() {
    // TODO Auto-generated method stub
    return null;
  }

  public void store(File f) {
    // TODO Auto-generated method stub

  }

  public void store(OutputStream in) {
    // TODO Auto-generated method stub

  }
  
  public void setDeploymentMetadata (DeploymentMetadata dm) { 
    _deploymentMetadata = dm;
  }

}
