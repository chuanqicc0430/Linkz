package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * 
 * <b>描述: </b>用于序列化组件，为序列化组件在生成某一字段序列化代码时提供这个字段的字段信息
 * <p>
 * <b>功能: </b>提供字段的字段信息
 * <p>
 * <b>用法: </b>该类由序列化组件调用,外部无需调用
 * <p>
 * 
 * @author 
 * 
 */
public final class FieldInformation implements Cloneable {

	private ProtoMember protoMember;

	private Class<?> outterClazz;

	private Field field;

	private int number;

	private boolean required;

	private String timezone;

	private Type currentType;

	private int currentNumber;

	public FieldInformation(Class<?> outterClazz, Field field, ProtoMember protoMember) {
		init(outterClazz, field, protoMember);
	}

	private final void init(Class<?> outterClazz, Field field, ProtoMember protoMember) {
		this.outterClazz = outterClazz;
		this.protoMember = protoMember;
		this.field = field;
		this.number = protoMember.value();
		this.required = protoMember.required();
		this.timezone = protoMember.timezone();
		this.currentNumber = protoMember.value();
		this.currentType = field.getGenericType();
	}

	public final FieldInformation clone() {
		FieldInformation fieldInformation = new FieldInformation(this.outterClazz, this.field, this.protoMember);
		fieldInformation.number = number;
		fieldInformation.required = required;
		fieldInformation.timezone = timezone;
		fieldInformation.currentType = currentType;
		fieldInformation.currentNumber = currentNumber;
		return fieldInformation;
	}

	public final ProtoMember getProtoMember() {
		return protoMember;
	}

	public final Field getField() {
		return field;
	}

	public final int getNumber() {
		return number;
	}

	public final FieldInformation setNumber(int number) {
		FieldInformation fieldInformation = this.clone();
		fieldInformation.number = number;
		return fieldInformation;
	}

	public final boolean isRequired() {
		return required;
	}

	public final FieldInformation setRequired(boolean required) {
		FieldInformation fieldInformation = this.clone();
		fieldInformation.required = required;
		return fieldInformation;
	}

	public final String getTimezone() {
		return timezone;
	}

	public final FieldInformation setTimezone(String timezone) {
		FieldInformation fieldInformation = this.clone();
		fieldInformation.timezone = timezone;
		return fieldInformation;
	}

	public final FieldInformation setProtoMember(ProtoMember protoMember) {
		FieldInformation fieldInformation = this.clone();
		fieldInformation.protoMember = protoMember;
		return fieldInformation;
	}

	public final FieldInformation setField(Field field) {
		FieldInformation fieldInformation = this.clone();
		fieldInformation.field = field;
		return fieldInformation;
	}

	public final Type getCurrentType() {
		return currentType;
	}

	public final FieldInformation setCurrentType(Type currentType) {
		FieldInformation fieldInformation = this.clone();
		fieldInformation.currentType = currentType;
		return fieldInformation;
	}

	public final int getCurrentNumber() {
		return currentNumber;
	}

	public final FieldInformation setCurrentNumber(int currentNumber) {
		FieldInformation fieldInformation = this.clone();
		fieldInformation.currentNumber = currentNumber;
		return fieldInformation;
	}

	public final Class<?> getOutterClass() {
		return outterClazz;
	}
}
