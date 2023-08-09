package it.nextworks.nfvmano.libs.vs.common.nsmf.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum NetworkSliceInstanceStatus {

    CREATED,
    INSTANTIATING,
    INSTANTIATED,
    CONFIGURING,
    TERMINATING,
    TERMINATED,
    FAILED,
    OTHER

}
