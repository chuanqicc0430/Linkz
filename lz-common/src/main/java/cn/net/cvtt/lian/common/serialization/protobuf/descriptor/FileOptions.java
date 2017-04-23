package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * FileOptions
 * 
 * @author 
 */

public class FileOptions extends ProtoEntity {

    @ProtoMember(1)
    private String java_package;

    public String getJava_package() {
      return java_package;
    }

    public FileOptions setJava_package(String value) {
      this.java_package = value;
      return this;
    }

    @ProtoMember(8)
    private String java_outer_classname;

    public String getJava_outer_classname() {
      return java_outer_classname;
    }

    public FileOptions setJava_outer_classname(String value) {
      this.java_outer_classname = value;
      return this;
    }

    @ProtoMember(10)
    private boolean java_multiple_files;

    public boolean getJava_multiple_files() {
      return java_multiple_files;
    }

    public FileOptions setJava_multiple_files(boolean value) {
      this.java_multiple_files = value;
      return this;
    }

    @ProtoMember(20)
    private boolean java_generate_equals_and_hash;

    public boolean getJava_generate_equals_and_hash() {
      return java_generate_equals_and_hash;
    }

    public FileOptions setJava_generate_equals_and_hash(boolean value) {
      this.java_generate_equals_and_hash = value;
      return this;
    }

    @ProtoMember(9)
    private OptimizeMode optimize_for;

    public OptimizeMode getOptimize_for() {
      return optimize_for;
    }

    public FileOptions setOptimize_for(OptimizeMode value) {
      this.optimize_for = value;
      return this;
    }

    @ProtoMember(16)
    private boolean cc_generic_services;

    public boolean getCc_generic_services() {
      return cc_generic_services;
    }

    public FileOptions setCc_generic_services(boolean value) {
      this.cc_generic_services = value;
      return this;
    }

    @ProtoMember(17)
    private boolean java_generic_services;

    public boolean getJava_generic_services() {
      return java_generic_services;
    }

    public FileOptions setJava_generic_services(boolean value) {
      this.java_generic_services = value;
      return this;
    }

    @ProtoMember(18)
    private boolean py_generic_services;

    public boolean getPy_generic_services() {
      return py_generic_services;
    }

    public FileOptions setPy_generic_services(boolean value) {
      this.py_generic_services = value;
      return this;
    }

}
