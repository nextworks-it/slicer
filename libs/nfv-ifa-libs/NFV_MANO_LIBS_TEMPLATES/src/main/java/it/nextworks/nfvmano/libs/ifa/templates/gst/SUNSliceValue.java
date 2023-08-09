package it.nextworks.nfvmano.libs.ifa.templates.gst;

public enum SUNSliceValue {
    AnyNetSlice, //can be used simultaneously with any network slice
    SameSSTdiffSD, //can be used simultaneously with any network slice with the same SST value but different SD values
    SameSDdiffSST, //can be used simultaneously with any network slice with the same SD value but different SST values
    CannotBeUsed, //cannot be used simultaneously with any network slice
    OpDefinedClass, //operator defined class
}
