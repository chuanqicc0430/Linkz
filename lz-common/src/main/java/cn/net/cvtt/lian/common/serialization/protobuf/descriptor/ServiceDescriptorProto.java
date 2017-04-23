package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import java.util.List;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * ServiceDescriptorProto
 * 
 * @author 
 */

public class ServiceDescriptorProto extends ProtoEntity {

    @ProtoMember(1)
    private String name;

    public String getName() {
      return name;
    }

    public ServiceDescriptorProto setName(String value) {
      name = value;
      return this;
    }

    @ProtoMember(2)
    private List<MethodDescriptorProto> method;

    public List<MethodDescriptorProto> getMethod() {
      return method;
    }

    public ServiceDescriptorProto setMethod(List<MethodDescriptorProto> value) {
      method = value;
      return this;
    }

}
