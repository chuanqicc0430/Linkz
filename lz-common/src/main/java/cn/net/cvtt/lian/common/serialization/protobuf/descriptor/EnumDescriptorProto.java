package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import java.util.List;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * EnumDescriptorProto
 * 
 * @author 
 */

public class EnumDescriptorProto extends ProtoEntity {

    @ProtoMember(1)
    private String name;

    public String getName() {
      return name;
    }

    public EnumDescriptorProto setName(String value) {
      name = value;
      return this;
    }

    @ProtoMember(2)
    private List<EnumValueDescriptorProto> value;

    public List<EnumValueDescriptorProto> getValue() {
      return value;
    }

    public EnumDescriptorProto setValue(List<EnumValueDescriptorProto> value) {
      this.value = value;
      return this;
    }

}
