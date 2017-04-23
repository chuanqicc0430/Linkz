package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * MethodDescriptorProto
 * 
 * @author 
 */

public class MethodDescriptorProto extends ProtoEntity {

    @ProtoMember(1)
    private String name;

    public String getName() {
      return name;
    }

    public MethodDescriptorProto setName(String value) {
      this.name = value;
      return this;
    }

    @ProtoMember(2)
    private String input_type;

    public String getInput_type() {
      return input_type;
    }

    public MethodDescriptorProto setInput_type(String value) {
      this.input_type = value;
      return this;
    }

    @ProtoMember(3)
    private String output_type;

    public String getOutput_type() {
      return output_type;
    }

    public MethodDescriptorProto setOutput_type(String value) {
      this.output_type = value;
      return this;
    }

}
