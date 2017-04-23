package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 字节码操作指令封装类，这里所谓的操作指令，是指操作码+操作值
 * 为一个指令，这些指令是从方法区的CodeAttribute的code[]中解析出来的，JVM运行到此部分时它会将它压入操作数栈中
 * 
 * @author 
 * 
 */
public abstract class Instruction {

	/** 操作码 */
	protected byte opcode;

	/** 当前指令长度(操作码+操作值) */
	protected int length = 1;

	/** 当前操作码起始处在整个code[]中的偏移量 */
	protected int offset = 0;

	/** 单向链表,链接下一个指令,用于指令流的分析 */
	private Instruction next;

	/**
	 * 构造一个操作指令
	 * 
	 * @param opcode
	 *            操作码
	 * @param opcodeValues
	 *            操作值
	 */
	public Instruction(byte opcode) {
		this.opcode = opcode;
	}

	public abstract void read(DataInput input) throws IOException;

	public Instruction getNext() {
		return next;
	}

	public void setNext(Instruction next) {
		this.next = next;
	}

	/**
	 * 解析codes中的操作指令信息
	 * 
	 * @param codes
	 * @return
	 */
	public static List<Instruction> readAll(byte[] codes) throws IOException {

		// 下面逐步遍历code中的内容
		int index = 0;
		boolean isWide = false;
		List<Instruction> list = new ArrayList<Instruction>();
		Instruction parentInstruction = null;
		DataInput input = new DataInputStream(new ByteArrayInputStream(codes));
		while (index < codes.length) {
			byte opcode = input.readByte();
			OpcodeEnum opcodeEnum = OpcodeEnum.valueOf(opcode);
			Instruction instruction = opcodeEnum.getInstructionEnum().newInstance(opcode, isWide);
			instruction.setOffset(index);
			instruction.read(input);
			index += instruction.getLength();
			list.add(instruction);
			if (parentInstruction != null) {
				parentInstruction.setNext(instruction);
			}
			parentInstruction = instruction;
			isWide = opcodeEnum == OpcodeEnum.WIDE ? true : false;
		}

		return list;
	}

	/**
	 * 解析codes中的操作指令信息
	 * 
	 * @param codes
	 * @return
	 */
	public static Instruction read(byte[] codes) throws IOException {

		// 下面逐步遍历code中的内容
		int index = 0;
		boolean isWide = false;
		Instruction instruction = null;
		Instruction parentInstruction = null;
		DataInput input = new DataInputStream(new ByteArrayInputStream(codes));
		while (index < codes.length && isWide) {
			byte opcode = input.readByte();
			OpcodeEnum opcodeEnum = OpcodeEnum.valueOf(opcode);
			instruction = opcodeEnum.getInstructionEnum().newInstance(opcode, isWide);
			instruction.setOffset(index);
			instruction.read(input);
			index += instruction.getLength();
			if (parentInstruction != null) {
				parentInstruction.setNext(instruction);
			}
			parentInstruction = instruction;
			isWide = opcodeEnum == OpcodeEnum.WIDE ? true : false;
		}

		return instruction;
	}

	public int getLength() {
		return length;
	}

	public byte getOpcode() {
		return opcode;
	}

	public int getOffset() {
		return offset;
	}

	void setOffset(int offset) {
		this.offset = offset;
	}

}
