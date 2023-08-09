/*
 * Copyright 2018 Nextworks s.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin;

public abstract class Plugin {

    protected String pluginId;
    protected PluginType pluginType;
    protected PluginOperationalState pluginOperationalState;
    //KAFKA config attributes

    public Plugin(String pluginId, PluginType pluginType) {
        this.pluginId = pluginId;
        this.pluginType = pluginType;
    }

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public PluginType getPluginType() {
        return pluginType;
    }

    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    public PluginOperationalState getPluginOperationalState() {
        return pluginOperationalState;
    }

    public void setPluginOperationalState(PluginOperationalState pluginOperationalState) {
        this.pluginOperationalState = pluginOperationalState;
    }

    public abstract void init();

}
