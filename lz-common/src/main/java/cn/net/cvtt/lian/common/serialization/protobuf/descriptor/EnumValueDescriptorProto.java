package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * EnumValueDescriptorProto
 * 
 * @author 
 */

public class EnumValueDescriptorProto extends ProtoEntity {

    @ProtoMember(1)
    private String name;

    public String getName() {
      return name;
    }

    public EnumValueDescriptorProto setName(String value) {
      name = value;
      return this;
    }

    @ProtoMember(2)
    private int number;

    public int getNumber() {
      return number;
    }

    public EnumValueDescriptorProto setNumber(int value) {
      number = value;
      return this;
    }

}
