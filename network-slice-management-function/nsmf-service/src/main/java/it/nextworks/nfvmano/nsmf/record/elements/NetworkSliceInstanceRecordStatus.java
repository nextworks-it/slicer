package it.nextworks.nfvmano.nsmf.record.elements;

import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstanceStatus;

public enum NetworkSliceInstanceRecordStatus {
    CREATED {
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.CREATED;
        }
    },
    COMPUTING_RESOURCE_ALLOCATION{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.INSTANTIATING;
        }
    },
    INSTANTIATING_CORE_SUBNET{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.INSTANTIATING;
        }
    },
    INSTANTIATING_TRANSPORT_SUBNET{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.INSTANTIATING;
        }
    },

    INSTANTIATING_RAN_SUBNET{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.INSTANTIATING;
        }
    },
    INSTANTIATING_APP_SUBNET{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.INSTANTIATING;
        }
    },
    CONFIGURING_TRAFFIC_FLOWS{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.INSTANTIATING;
        }
    },
    CONFIGURING{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.CONFIGURING;
        }
    },
    CONFIGURING_MONITORING{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.INSTANTIATING;
        }
    },
    INSTANTIATED{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.INSTANTIATED;
        }
    },
    DEALLOCATING_TRAFFIC_FLOWS{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.TERMINATING;
        }
    },
    DEALLOCATING_MONITORING{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.TERMINATING;
        }
    },
    TERMINATING_RAN_SUBNET{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.TERMINATING;
        }
    },
    TERMINATING_APP_SUBNET{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.TERMINATING;
        }
    },
    TERMINATING_TRANSPORT_SUBNET{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.TERMINATING;
        }
    },
    TERMINATING_CORE_SUBNET{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.TERMINATING;
        }
    },
    TERMINATED{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.TERMINATED;
        }
    },
    FAILED{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.FAILED;
        }
    },;
    public NetworkSliceInstanceStatus asNsiStatus(){
        return NetworkSliceInstanceStatus.OTHER;
    }

}
