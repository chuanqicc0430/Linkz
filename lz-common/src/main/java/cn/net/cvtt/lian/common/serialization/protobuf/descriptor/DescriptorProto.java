package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import java.util.List;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * message描述
 * 
 * @author 
 */

public class DescriptorProto extends ProtoEntity {

	/** message名称 */
    @ProtoMember(1)
    private String name;

    public String getName() {
      return name;
    }

    public DescriptorProto setName(String value) {
      this.name = value;
      return this;
    }

    /** message字段列表 */
    @ProtoMember(2)
    private List<FieldDescriptorProto> field;

    public List<FieldDescriptorProto> getField() {
      return field;
    }

    public DescriptorProto setField(List<FieldDescriptorProto> value) {
      this.field = value;
      return this;
    }

    @ProtoMember(6)
    private List<FieldDescriptorProto> extension;

    public List<FieldDescriptorProto> getExtension() {
      return extension;
    }

    public DescriptorProto setExtension(List<FieldDescriptorProto> value) {
      this.extension = value;
      return this;
    }

    @ProtoMember(3)
    private List<DescriptorProto> nested_type;

    public List<DescriptorProto> getNested_type() {
      return nested_type;
    }

    public DescriptorProto setNested_type(List<DescriptorProto> value) {
      this.nested_type = value;
      return this;
    }

    @ProtoMember(4)
    private List<EnumDescriptorProto> enum_type;

    public List<EnumDescriptorProto> getEnum_type() {
      return enum_type;
    }

    public DescriptorProto setEnum_type(List<EnumDescriptorProto> value) {
      this.enum_type = value;
      return this;
    }

}
