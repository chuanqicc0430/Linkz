package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import java.util.List;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * .proto file self descriptor
 * 
 * use protoc to generate FileDescriptorSet PB Data
 * 
 * * @author 
 */

public class FileDescriptorSet extends ProtoEntity {

    @ProtoMember(1)
    private List<FileDescriptorProto> file;

    public List<FileDescriptorProto> getFile() {
      return file;
    }

    public FileDescriptorSet setFile(List<FileDescriptorProto> value) {
      file = value;
      return this;
    }

}
