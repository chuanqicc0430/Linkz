package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import cn.net.cvtt.lian.common.serialization.bytecode.type.CPInfo;
import cn.net.cvtt.lian.common.serialization.bytecode.type.ClassFile;

/**
 * 标识其中指向常量池中的数据
 * 
 * @author 
 * 
 */
public abstract class ConstantInfoInstruction extends Instruction {

	public ConstantInfoInstruction(byte opcode) {
		super(opcode);
	}

	public abstract int getValue();

	public CPInfo getCpInfo(ClassFile classFile) {
		return getValue() < classFile.getConstant_pool_count() ? classFile.getConstant_pool()[getValue()] : null;
	}

}
