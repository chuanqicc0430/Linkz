package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;

/**
 * 为IINC操作码准备的指令
 * 
 * @author 
 * 
 */
public class IINCInstruction extends ConstantInfoInstruction {

	/** 0xc4 wide 扩展访问局部变量表的索引宽度，一旦前一个指令是它，那么此时就要多取一个宽度的操作值了 */
	private boolean wide;
	private int value;

	public IINCInstruction(byte opcode, boolean wide) {
		super(opcode);
		this.wide = wide;
	}

	@Override
	public void read(DataInput input) throws IOException {

		if (wide) {
			// 如果前一个的操作码是wide，那么此时需要读取两个byte长度的无符号数字
			value = input.readUnsignedShort();
			value = input.readUnsignedShort();
			super.length = 5;
		} else {
			// 如果前面没有加扩展访问局部变量表的索引宽度的标示，那么取一个无符号byte既可
			value = input.readUnsignedByte();
			value = input.readUnsignedByte();
			super.length = 3;
		}
	}

	public int getValue() {
		return value;
	}

}
