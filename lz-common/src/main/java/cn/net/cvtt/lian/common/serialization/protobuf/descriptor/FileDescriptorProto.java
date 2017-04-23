package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import java.util.List;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * FileDescriptorProto
 * 
 * @author 
 */

public class FileDescriptorProto extends ProtoEntity {

    @ProtoMember(1)
    private String name;

    public String getName() {
      return name;
    }

    public FileDescriptorProto setName(String value) {
      this.name = value;
      return this;
    }

    @ProtoMember(2)
    private String package_name;

    public String getPackage_name() {
      return package_name;
    }

    public FileDescriptorProto setPackage_name(String value) {
      this.package_name = value;
      return this;
    }

    @ProtoMember(3)
    private List<String> dependency;

    public List<String> getDependency() {
      return dependency;
    }

    public FileDescriptorProto setDependency(List<String> value) {
      this.dependency = value;
      return this;
    }

    @ProtoMember(4)
    private List<DescriptorProto> message_type;

    public List<DescriptorProto> getMessage_type() {
      return message_type;
    }

    public FileDescriptorProto setMessage_type(List<DescriptorProto> value) {
      this.message_type = value;
      return this;
    }

    @ProtoMember(5)
    private List<EnumDescriptorProto> enum_type;

    public List<EnumDescriptorProto> getEnum_type() {
      return enum_type;
    }

    public FileDescriptorProto setEnum_type(List<EnumDescriptorProto> value) {
      this.enum_type = value;
      return this;
    }

    @ProtoMember(6)
    private List<ServiceDescriptorProto> service;

    public List<ServiceDescriptorProto> getService() {
      return service;
    }

    public FileDescriptorProto setService(List<ServiceDescriptorProto> value) {
      this.service = value;
      return this;
    }

    @ProtoMember(7)
    private List<FieldDescriptorProto> extension;

    public List<FieldDescriptorProto> getExtension() {
      return extension;
    }

    public FileDescriptorProto setExtension(List<FieldDescriptorProto> value) {
      this.extension = value;
      return this;
    }

    @ProtoMember(8)
    private FileOptions options;

    public FileOptions getOptions() {
      return options;
    }

    public FileDescriptorProto setOptions(FileOptions value) {
      this.options = value;
      return this;
    }

}
