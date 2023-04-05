package it.nextworks.nfvmano.nsmf.record.elements;

import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstanceStatus;

public enum NetworkSliceSubnetRecordStatus {
    INSTANTIATING{
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.INSTANTIATING;
        }
    },
    INSTANTIATED {
                @Override
                public NetworkSliceInstanceStatus asNsiStatus() {
                    return NetworkSliceInstanceStatus.INSTANTIATED;
                }
            },
    FAILED  {
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.FAILED;
        }
    },
    TERMINATING  {
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.TERMINATING;
        }
    },
    TERMINATED  {
        @Override
        public NetworkSliceInstanceStatus asNsiStatus() {
            return NetworkSliceInstanceStatus.TERMINATED;
        }
    };

    public NetworkSliceInstanceStatus asNsiStatus(){
        return NetworkSliceInstanceStatus.OTHER;
    }
}
