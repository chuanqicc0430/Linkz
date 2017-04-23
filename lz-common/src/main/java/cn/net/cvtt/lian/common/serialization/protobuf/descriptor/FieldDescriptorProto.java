package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * 字段描述
 * 
 * @author 
 */

public class FieldDescriptorProto extends ProtoEntity {

	/** 字段名称 */
	@ProtoMember(1)
	private String name;

	public String getName() {
		return name;
	}

	public FieldDescriptorProto setName(String value) {
		this.name = value;
		return this;
	}

	/** 字段tag */
	@ProtoMember(3)
	private int number;

	public int getNumber() {
		return number;
	}

	public FieldDescriptorProto setNumber(int value) {
		this.number = value;
		return this;
	}

	/** */
	@ProtoMember(4)
	private FieldLabel label;

	public FieldLabel getLabel() {
		return label;
	}

	public FieldDescriptorProto setLabel(FieldLabel value) {
		this.label = value;
		return this;
	}

	private String label_name;

	public String getLabel_name() {
		if (label_name == null)
			label_name = label.getName();
		return label_name;
	}

	/** 字段类型 */
	@ProtoMember(5)
	private FieldType type;

	public FieldType getType() {
		return type;
	}

	public FieldDescriptorProto setType(FieldType value) {
		this.type = value;
		return this;
	}

	private String proto_type_name;

	public String getProto_type_name() {
		if (type == FieldType.TYPE_ENUM || type == FieldType.TYPE_MESSAGE) {
			proto_type_name = type_name.substring(1);
		}
		if (proto_type_name == null) {
			proto_type_name = type.getName();
		}
		return proto_type_name;
	}

	/** 复杂类型的名称 */
	@ProtoMember(6)
	private String type_name;

	public String getType_name() {
		return type_name;
	}

	public FieldDescriptorProto setType_name(String value) {
		this.type_name = value;
		return this;
	}

	@ProtoMember(2)
	private String extendee;

	public String getExtendee() {
		return extendee;
	}

	public FieldDescriptorProto setExtendee(String value) {
		this.extendee = value;
		return this;
	}

	@ProtoMember(7)
	private String default_value;

	public String getDefault_value() {
		return default_value;
	}

	public FieldDescriptorProto setDefault_value(String value) {
		this.default_value = value;
		return this;
	}

}
