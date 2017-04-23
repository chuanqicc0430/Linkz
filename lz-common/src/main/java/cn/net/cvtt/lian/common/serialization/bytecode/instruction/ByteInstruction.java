package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;

/**
 * 带一个byte类型操作值的操作码，这样的类型在204个操作码中占了14个
 * 
 * @author 
 * 
 */
public class ByteInstruction extends ConstantInfoInstruction {

	/** 0xc4 wide 扩展访问局部变量表的索引宽度，一旦前一个指令是它，那么此时就要多取一个宽度的操作值了 */
	private boolean wide;
	private int value;

	public ByteInstruction(byte opcode, boolean wide) {
		super(opcode);
		this.wide = wide;
	}

	public ByteInstruction(byte opcode, int value, boolean wide) {
		super(opcode);
		this.value = value;
		this.wide = wide;
	}

	@Override
	public void read(DataInput input) throws IOException {
		if (wide) {
			// 如果前一个的操作码是wide，那么此时需要读取两个byte长度的无符号数字
			value = input.readUnsignedShort();
			super.length = 3;
		} else {
			// 如果前面没有加扩展访问局部变量表的索引宽度的标示，那么取一个无符号byte既可
			value = input.readUnsignedByte();
			super.length = 2;
		}
	}

	public int getValue() {
		return value;
	}

	public boolean getWide() {
		return wide;
	}
}
