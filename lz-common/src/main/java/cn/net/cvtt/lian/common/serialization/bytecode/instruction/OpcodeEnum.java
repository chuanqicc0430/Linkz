package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个类是操作码对应的指令以及助记符枚举列表，整理起来好累...
 * @author 
 *
 */
public enum OpcodeEnum {
	NOP((byte) 0x00, "nop", "什么都不做。"),
	ACONST_NULL((byte) 0x01, "aconst_null", "将null推送至栈顶。"),
	ICONST_M1((byte) 0x02, "iconst_m1", "将int型-1推送至栈顶。"),
	ICONST_0((byte) 0x03, "iconst_0", "将int型0推送至栈顶。"),
	ICONST_1((byte) 0x04, "iconst_1", "将int型1推送至栈顶。"),
	ICONST_2((byte) 0x05, "iconst_2", "将int型2推送至栈顶。"),
	ICONST_3((byte) 0x06, "iconst_3", "将int型3推送至栈顶。"),
	ICONST_4((byte) 0x07, "iconst_4", "将int型4推送至栈顶。"),
	ICONST_5((byte) 0x08, "iconst_5", "将int型5推送至栈顶。"),
	LCONST_0((byte) 0x09, "lconst_0", "将long型0推送至栈顶。"),
	LCONST_1((byte) 0x0a, "lconst_1", "将long型1推送至栈顶。"),
	FCONST_0((byte) 0x0b, "fconst_0", "将float型0推送至栈顶。"),
	FCONST_1((byte) 0x0c, "fconst_1", "将float型1推送至栈顶。"),
	FCONST_2((byte) 0x0d, "fconst_2", "将float型2推送至栈顶。"),
	DCONST_0((byte) 0x0e, "dconst_0", "将double型0推送至栈顶。"),
	DCONST_1((byte) 0x0f, "dconst_1", "将double型1推送至栈顶。"),
	BIPUSH((byte) 0x10, InstructionEnum.BYTE_INSTRUCTION, "bipush", "将单字节的常量值（-128~127）推送至栈顶。"),
	SIPUSH((byte) 0x11, InstructionEnum.SHORT_INSTRUCTION, "sipush", "将一个短整型常量值（-32768~32767）推送至栈顶。"),
	LDC((byte) 0x12, InstructionEnum.BYTE_INSTRUCTION, "ldc", "将int，float或String型常量值从常量池中推送至栈顶。"),
	LDC_W((byte) 0x13, InstructionEnum.SHORT_INSTRUCTION, "ldc_w", "将int，float或String型常量值从常量池中推送至栈顶（宽索引"),
	LDC2_W((byte) 0x14, InstructionEnum.SHORT_INSTRUCTION, "ldc2_w", "将long或double型常量值从常量池中推送至栈顶（宽索引）。"),
	ILOAD((byte) 0x15, InstructionEnum.BYTE_INSTRUCTION, "iload", "将指定的int型局部变量推送至栈顶。"),
	LLOAD((byte) 0x16, InstructionEnum.BYTE_INSTRUCTION, "lload", "将指定的long型局部变量推送至栈顶。"),
	FLOAD((byte) 0x17, InstructionEnum.BYTE_INSTRUCTION, "fload", "将指定的float型局部变量推送至栈顶。"),
	DLOAD((byte) 0x18, InstructionEnum.BYTE_INSTRUCTION, "dload", "将指定的double型局部变量推送至栈顶。"),
	ALOAD((byte) 0x19, InstructionEnum.BYTE_INSTRUCTION, "aload", "将指定的引用类型局部变量推送至栈顶。"),
	ILOAD_0((byte) 0x1a, "iload_0", "将第一个int型局部变量推送至栈顶。"),
	ILOAD_1((byte) 0x1b, "iload_1", "将第二个int型局部变量推送至栈顶。"),
	ILOAD_2((byte) 0x1c, "iload_2", "将第三个int型局部变量推送至栈顶。"),
	ILOAD_3((byte) 0x1d, "iload_3", "将第四个int型局部变量推送至栈顶。"),
	LLOAD_0((byte) 0x1e, "lload_0", "将第一个long型局部变量推送至栈顶。"),
	LLOAD_1((byte) 0x1f, "lload_1", "将第二个long型局部变量推送至栈顶。"),
	LLOAD_2((byte) 0x20, "lload_2", "将第三个long型局部变量推送至栈顶。"),
	LLOAD_3((byte) 0x21, "lload_3", "将第四个long型局部变量推送至栈顶。"),
	FLOAD_0((byte) 0x22, "fload_0", "将第一个float型局部变量推送至栈顶。"),
	FLOAD_1((byte) 0x23, "fload_1", "将第二个float型局部变量推送至栈顶。"),
	FLOAD_2((byte) 0x24, "fload_2", "将第三个float型局部变量推送至栈顶"),
	FLOAD_3((byte) 0x25, "fload_3", "将第四个float型局部变量推送至栈顶。"),
	DLOAD_0((byte) 0x26, "dload_0", "将第一个double型局部变量推送至栈顶。"),
	DLOAD_1((byte) 0x27, "dload_1", "将第二个double型局部变量推送至栈顶。"),
	DLOAD_2((byte) 0x28, "dload_2", "将第三个double型局部变量推送至栈顶。"),
	DLOAD_3((byte) 0x29, "dload_3", "将第四个double型局部变量推送至栈顶。"),
	ALOAD_0((byte) 0x2a, "aload_0", "将第一个引用类型局部变量推送至栈顶。"),
	ALOAD_1((byte) 0x2b, "aload_1", "将第二个引用类型局部变量推送至栈顶。"),
	ALOAD_2((byte) 0x2c, "aload_2", "将第三个引用类型局部变量推送至栈顶。"),
	ALOAD_3((byte) 0x2d, "aload_3", "将第四个引用类型局部变量推送至栈顶。"),
	IALOAD((byte) 0x2e, "iaload", "将int型数组指定索引的值推送至栈顶。"),
	LALOAD((byte) 0x2f, "laload", "将long型数组指定索引的值推送至栈"),
	FALOAD((byte) 0x30, "faload", "将float型数组指定索引的值推送至栈顶。"),
	DALOAD((byte) 0x31, "daload", "将double型数组指定索引的值推送至栈顶。"),
	AALOAD((byte) 0x32, "aaload", "将引用型数组指定索引的值推送至栈顶。"),
	BALOAD((byte) 0x33, "baload", "将boolean或byte型数组指定索引的值推送至栈顶。"),
	CALOAD((byte) 0x34, "caload", "将char型数组指定索引的值推送至栈顶。"),
	SALOAD((byte) 0x35, "saload", "将short型数组指定索引的值推送至栈顶。"),
	ISTORE((byte) 0x36, InstructionEnum.BYTE_INSTRUCTION, "istore", "将栈顶int型数值存入指定局部变量。"),
	LSTORE((byte) 0x37, InstructionEnum.BYTE_INSTRUCTION, "lstore", "将栈顶long型数值存入指定局部变量。"),
	FSTORE((byte) 0x38, InstructionEnum.BYTE_INSTRUCTION, "fstore", "将栈顶float型数值存入指定局部变量。"),
	DSTORE((byte) 0x39, InstructionEnum.BYTE_INSTRUCTION, "dstore", "将栈顶double型数值存入指定局部变量。"),
	ASTORE((byte) 0x3a, InstructionEnum.BYTE_INSTRUCTION, "astore", "将栈顶引用型数值存入指定局部变量。"),
	ISTORE_0((byte) 0x3b, "istore_0", "将栈顶int型数值存入第一个局部变量。"),
	ISTORE_1((byte) 0x3c, "istore_1", "将栈顶int型数值存入第二个局部变量。"),
	ISTORE_2((byte) 0x3d, "istore_2", "将栈顶int型数值存入第三个局部变量。"),
	ISTORE_3((byte) 0x3e, "istore_3", "将栈顶int型数值存入第四个局部变量。"),
	LSTORE_0((byte) 0x3f, "lstore_0", "将栈顶long型数值存入第一个局部变量。"),
	LSTORE_1((byte) 0x40, "lstore_1", "将栈顶long型数值存入第二个局部变量。"),
	LSTORE_2((byte) 0x41, "lstore_2", "将栈顶long型数值存入第三个局部变量。"),
	LSTORE_3((byte) 0x42, "lstore_3", "将栈顶long型数值存入第四个局部变量。"),
	FSTORE_0((byte) 0x43, "fstore_0", "将栈顶float型数值存入第一个局部变量。"),
	FSTORE_1((byte) 0x44, "fstore_1", "将栈顶float型数值存入第二个局部变量。"),
	FSTORE_2((byte) 0x45, "fstore_2", "将栈顶float型数值存入第三个局部变量。"),
	FSTORE_3((byte) 0x46, "fstore_3", "将栈顶float型数值存入第四个局部变量。"),
	DSTORE_0((byte) 0x47, "dstore_0", "将栈顶double型数值存入第一个局部变量。"),
	DSTORE_1((byte) 0x48, "dstore_1", "将栈顶double型数值存入第二个局部变量。"),
	DSTORE_2((byte) 0x49, "dstore_2", "将栈顶double型数值存入第三个局部变量。"),
	DSTORE_3((byte) 0x4a, "dstore_3", "将栈顶double型数值存入第四个局部变量。"),
	ASTORE_0((byte) 0x4b, "astore_0", "将栈顶引用型数值存入第一个局部变量。"),
	ASTORE_1((byte) 0x4c, "astore_1", "将栈顶引用型数值存入第二个局部变量。"),
	ASTORE_2((byte) 0x4d, "astore_2", "将栈顶引用型数值存入第三个局部变量。"),
	ASTORE_3((byte) 0x4e, "astore_3", "将栈顶引用型数值存入第四个局部变量。"),
	IASTORE((byte) 0x4f, "iastore", "将栈顶int型数值存入指定数组的指定索引位置"),
	LASTORE((byte) 0x50, "lastore", "将栈顶long型数值存入指定数组的指定索引位置。"),
	FASTORE((byte) 0x51, "fastore", "将栈顶float型数值存入指定数组的指定索引位置。"),
	DASTORE((byte) 0x52, "dastore", "将栈顶double型数值存入指定数组的指定索引位置。"),
	AASTORE((byte) 0x53, "aastore", "将栈顶引用型数值存入指定数组的指定索引位置。"),
	BASTORE((byte) 0x54, "bastore", "将栈顶boolean或byte型数值存入指定数组的指定索引位置。"),
	CASTORE((byte) 0x55, "castore", "将栈顶char型数值存入指定数组的指定索引位置"),
	SASTORE((byte) 0x56, "sastore", "将栈顶short型数值存入指定数组的指定索引位置。"),
	POP((byte) 0x57, "pop", "将栈顶数值弹出（数值不能是long或double类型的）。"),
	POP2((byte) 0x58, "pop2", "将栈顶的一个（long或double类型的）或两个数值弹出（其它）。"),
	DUP((byte) 0x59, "dup", "复制栈顶数值并将复制值压入栈顶。"),
	DUP_X1((byte) 0x5a, "dup_x1", "复制栈顶数值并将两个复制值压入栈顶。"),
	DUP_X2((byte) 0x5b, "dup_x2", "复制栈顶数值并将三个（或两个）复制值压入栈顶。"),
	DUP2((byte) 0x5c, "dup2", "复制栈顶一个（long或double类型的)或两个（其它）数值并将复制值压入栈顶。"),
	DUP2_X1((byte) 0x5d, "dup2_x1", "dup_x1指令的双倍版本。"),
	DUP2_X2((byte) 0x5e, "dup2_x2", "dup_x2指令的双倍版本。"),
	SWAP((byte) 0x5f, "swap", "将栈最顶端的两个数值互换（数值不能是long或double类型的）。"),
	IADD((byte) 0x60, "iadd", "将栈顶两int型数值相加并将结果压入栈顶。"),
	LADD((byte) 0x61, "ladd", "将栈顶两long型数值相加并将结果压入栈顶。"),
	FADD((byte) 0x62, "fadd", "将栈顶两float型数值相加并将结果压入栈顶。"),
	DADD((byte) 0x63, "dadd", "将栈顶两double型数值相加并将结果压入栈顶。"),
	ISUB((byte) 0x64, "isub", "将栈顶两int型数值相减并将结果压入栈"),
	LSUB((byte) 0x65, "lsub", "将栈顶两long型数值相减并将结果压入栈顶。"),
	FSUB((byte) 0x66, "fsub", "将栈顶两float型数值相减并将结果压入栈顶。"),
	DSUB((byte) 0x67, "dsub", "将栈顶两double型数值相减并将结果压入栈顶。"),
	IMUL((byte) 0x68, "imul", "将栈顶两int型数值相乘并将结果压入栈顶。。"),
	LMUL((byte) 0x69, "lmul", "将栈顶两long型数值相乘并将结果压入栈顶。"),
	FMUL((byte) 0x6a, "fmul", "将栈顶两float型数值相乘并将结果压入栈顶。"),
	DMUL((byte) 0x6b, "dmul", "将栈顶两double型数值相乘并将结果压入栈顶。"),
	IDIV((byte) 0x6c, "idiv", "将栈顶两int型数值相除并将结果压入栈顶。"),
	LDIV((byte) 0x6d, "ldiv", "将栈顶两long型数值相除并将结果压入栈顶。"),
	FDIV((byte) 0x6e, "fdiv", "将栈顶两float型数值相除并将结果压入栈顶。"),
	DDIV((byte) 0x6f, "ddiv", "将栈顶两double型数值相除并将结果压入栈顶。"),
	IREM((byte) 0x70, "irem", "将栈顶两int型数值作取模运算并将结果压入栈顶。"),
	LREM((byte) 0x71, "lrem", "将栈顶两long型数值作取模运算并将结果压入栈顶。"),
	FREM((byte) 0x72, "frem", "将栈顶两float型数值作取模运算并将结果压入栈顶。"),
	DREM((byte) 0x73, "drem", "将栈顶两double型数值作取模运算并将结果压入栈顶。"),
	INEG((byte) 0x74, "ineg", "将栈顶int型数值取负并将结果压入栈顶。"),
	LNEG((byte) 0x75, "lneg", "将栈顶long型数值取负并将结果压入栈顶。"),
	FNEG((byte) 0x76, "fneg", "将栈顶float型数值取负并将结果压入栈顶。"),
	DNEG((byte) 0x77, "dneg", "将栈顶double型数值取负并将结果压入栈顶。"),
	ISHL((byte) 0x78, "ishl", "将int型数值左移位指定位数并将结果压入栈顶。"),
	LSHL((byte) 0x79, "lshl", "将long型数值左移位指定位数并将结果压入栈顶。"),
	ISHR((byte) 0x7a, "ishr", "将int型数值右（有符号）移位指定位数并将结果压入栈顶。"),
	LSHR((byte) 0x7b, "lshr", "将long型数值右（有符号）移位指定位数并将结果压入栈顶。"),
	IUSHR((byte) 0x7c, "iushr", "将int型数值右（无符号）移位指定位数并将结果压入栈顶。"),
	LUSHR((byte) 0x7d, "lushr", "将long型数值右（无符号）移位指定位数并将结果压入栈顶。"),
	IAND((byte) 0x7e, "iand", "将栈顶两int型数值作“按位与”并将结果压入栈顶。"),
	LAND((byte) 0x7f, "land", "将栈顶两long型数值作“按位与”并将结果压入栈顶。"),
	IOR((byte) 0x80, "ior", "将栈顶两int型数值作“按位或”并将结果压入栈"),
	LOR((byte) 0x81, "lor", "将栈顶两long型数值作“按位或”并将结果压入栈顶。"),
	IXOR((byte) 0x82, "ixor", "将栈顶两int型数值作“按位异或”并将结果压入栈顶。"),
	LXOR((byte) 0x83, "lxor", "将栈顶两long型数值作“按位异或”并将结果压入栈顶。"),
	IINC((byte) 0x84, InstructionEnum.IINC_INSTRUCTION, "iinc", "将指定int型变量增加指定值。"),//暂时使用, InstructionEnum.BYTE_INSTRUCTION
	I2L((byte) 0x85, "i2l", "将栈顶int型数值强制转换成long型数值并将结果压入栈顶。"),
	I2F((byte) 0x86, "i2f", "将栈顶int型数值强制转换成float型数值并将结果压入栈顶。"),
	I2D((byte) 0x87, "i2d", "将栈顶int型数值强制转换成double型数值并将结果压入栈顶。"),
	L2I((byte) 0x88, "l2i", "将栈顶long型数值强制转换成int型数值并将结果压入栈顶。"),
	L2F((byte) 0x89, "l2f", "将栈顶long型数值强制转换成float型数值并将结果压入栈顶。"),
	L2D((byte) 0x8a, "l2d", "将栈顶long型数值强制转换成double型数值并将结果压入栈顶。"),
	F2I((byte) 0x8b, "f2i", "将栈顶float型数值强制转换成int型数值并将结果压入栈顶。"),
	F2L((byte) 0x8c, "f2l", "将栈顶float型数值强制转换成long型数值并将结果压入栈顶。"),
	F2D((byte) 0x8d, "f2d", "将栈顶float型数值强制转换成double型数值并将结果压入栈顶。"),
	D2I((byte) 0x8e, "d2i", "将栈顶double型数值强制转换成int型数值并将结果压入栈顶。"),
	D2L((byte) 0x8f, "d2l", "将栈顶double型数值强制转换成long型数值并将结果压入栈顶。"),
	D2F((byte) 0x90, "d2f", "将栈顶double型数值强制转换成float型数值并将结果压入栈顶。"),
	I2B((byte) 0x91, "i2b", "将栈顶int型数值强制转换成byte型数值并将结果压入栈顶。"),
	I2C((byte) 0x92, "i2c", "将栈顶int型数值强制转换成char型数值并将结果压入栈顶。"),
	I2S((byte) 0x93, "i2s", "将栈顶int型数值强制转换成short型数值并将结果压入栈顶。"),
	LCMP((byte) 0x94, "lcmp", "比较栈顶两long型数值大小，并将结果（1，0，-1）压入栈"),
	FCMPL((byte) 0x95, "fcmpl", "比较栈顶两float型数值大小，并将结果（1，0，-1）压入栈顶；当其中一个数值为“NaN”时，将-1压入栈顶。"),
	FCMPG((byte) 0x96, "fcmpg", "比较栈顶两float型数值大小，并将结果（1，0，-1）压入栈顶；当其中一个数值为“NaN”时，将1压入栈顶。"),
	DCMPL((byte) 0x97, "dcmpl", "比较栈顶两double型数值大小，并将结果（1，0，-1）压入栈顶；当其中一个数值为“NaN”时，将-1压入栈顶。"),
	DCMPG((byte) 0x98, "dcmpg", "比较栈顶两double型数值大小，并将结果（1，0，-1）压入栈顶；当其中一个数值为“NaN”时，将1压入栈顶。"),
	IFEQ((byte) 0x99, InstructionEnum.BRANCH_INSTRUCTION, "ifeq", "当栈顶int型数值等于0时跳转。"),
	IFNE((byte) 0x9a, InstructionEnum.BRANCH_INSTRUCTION, "ifne", "当栈顶int型数值不等于0时跳转。"),
	IFLT((byte) 0x9b, InstructionEnum.BRANCH_INSTRUCTION, "iflt", "当栈顶int型数值小于0时跳转。"),
	IFGE((byte) 0x9c, InstructionEnum.BRANCH_INSTRUCTION, "ifge", "当栈顶int型数值大于等于0时跳转。"),
	IFGT((byte) 0x9d, InstructionEnum.BRANCH_INSTRUCTION, "ifgt", "当栈顶int型数值大于0时跳转。"),
	IFLE((byte) 0x9e, InstructionEnum.BRANCH_INSTRUCTION, "ifle", "当栈顶int型数值小于等于0时跳转。"),
	IF_ICMPEQ((byte) 0x9f, InstructionEnum.BRANCH_INSTRUCTION, "if_icmpeq", "比较栈顶两int型数值大小，当结果等于0时跳转。"),
	IF_ICMPNE((byte) 0xa0, InstructionEnum.BRANCH_INSTRUCTION, "if_icmpne", "比较栈顶两int型数值大小，当结果不等于0时跳转。"),
	IF_ICMPLT((byte) 0xa1, InstructionEnum.BRANCH_INSTRUCTION, "if_icmplt", "比较栈顶两int型数值大小，当结果小于0时跳转。"),
	IF_ICMPGE((byte) 0xa2, InstructionEnum.BRANCH_INSTRUCTION, "if_icmpge", "比较栈顶两int型数值大小，当结果大于等于0时跳转。"),
	IF_ICMPGT((byte) 0xa3, InstructionEnum.BRANCH_INSTRUCTION, "if_icmpgt", "比较栈顶两int型数值大小，当结果大于0时跳转"),
	IF_ICMPLE((byte) 0xa4, InstructionEnum.BRANCH_INSTRUCTION, "if_icmple", "比较栈顶两int型数值大小，当结果小于等于0时跳转。"),
	IF_ACMPEQ((byte) 0xa5, InstructionEnum.BRANCH_INSTRUCTION, "if_acmpeq", "比较栈顶两引用型数值，当结果相等时跳转。"),
	IF_ACMPNE((byte) 0xa6, InstructionEnum.BRANCH_INSTRUCTION, "if_acmpne", "比较栈顶两引用型数值，当结果不相等时跳转。"),
	GOTO((byte) 0xa7, InstructionEnum.BRANCH_INSTRUCTION, "goto", "无条件跳转。"),
	JSR((byte) 0xa8, InstructionEnum.BRANCH_INSTRUCTION, "jsr", "跳转至指定16位offset位置，并将jsr下一条指令地址压入栈顶。"),
	RET((byte) 0xa9, InstructionEnum.BYTE_INSTRUCTION, "ret", "返回至局部变量指定的index的指令位置（一般与jsr，jsr_w联合使用）。"),
	TABLESWITCH((byte) 0xaa, InstructionEnum.TABLESWITCH_INSTRUCTION, "tableswitch", "用于switch条件跳转，case值连续（可变长度指令"),
	LOOKUPSWITCH((byte) 0xab, InstructionEnum.LOOKUPSWITCH_INSTRUCTION, "lookupswitch", "用于switch条件跳转，case值不连续（可变长度指令）。"),
	IRETURN((byte) 0xac, "ireturn", "从当前方法返回int。"),
	LRETURN((byte) 0xad, "lreturn", "从当前方法返回long。"),
	FRETURN((byte) 0xae, "freturn", "从当前方法返回float。"),
	DRETURN((byte) 0xaf, "dreturn", "从当前方法返回double。"),
	ARETURN((byte) 0xb0, "areturn", "从当前方法返回对象引用。"),
	RETURN((byte) 0xb1, "return", "从当前方法返回void。"),
	GETSTATIC((byte) 0xb2, InstructionEnum.SHORT_INSTRUCTION, "getstatic", "获取指定类的静态域，并将其值压入栈顶。"),
	PUTSTATIC((byte) 0xb3, InstructionEnum.SHORT_INSTRUCTION, "putstatic", "为指定的类的静态域赋值。"),
	GETFIELD((byte) 0xb4, InstructionEnum.SHORT_INSTRUCTION, "getfield", "获取指定类的实例域，并将其值压入栈顶。"),
	PUTFIELD((byte) 0xb5, InstructionEnum.SHORT_INSTRUCTION, "putfield", "为指定的类的实例域赋值。"),
	INVOKEVIRTUAL((byte) 0xb6, InstructionEnum.SHORT_INSTRUCTION, "invokevirtual", "调用实例方法。"),
	INVOKESPECIAL((byte) 0xb7, InstructionEnum.SHORT_INSTRUCTION, "invokespecial", "调用超类构造方法，实例初始化方法，私有方法。"),
	INVOKESTATIC((byte) 0xb8, InstructionEnum.SHORT_INSTRUCTION, "invokestatic", "调用静态方法。"),
	INVOKEINTERFACE((byte) 0xb9, InstructionEnum.INVOKEINTERFACE_INSTRUCTION, "invokeinterface", "调用接口方法。"),
	INVOKEDYNAMIC((byte) 0xba, "invokedynamic", "调用动态链接方法(JDK7中新增)。"),
	NEW((byte) 0xbb, InstructionEnum.SHORT_INSTRUCTION, "new", "创建一个对象，并将其引用值压入栈顶。"),
	NEWARRAY((byte) 0xbc, InstructionEnum.BYTE_INSTRUCTION, "newarray", "创建一个指定原始类型（如int、float、char??）的数组，并将其引用值压入栈顶。"),
	ANEWARRAY((byte) 0xbd, InstructionEnum.SHORT_INSTRUCTION, "anewarray", "创建一个引用型（如类，接口，数组）的数组，并将其引用值压入栈顶。"),
	ARRAYLENGTH((byte) 0xbe, "arraylength", "获得数组的长度值并压入栈顶。"),
	ATHROW((byte) 0xbf, "athrow", "将栈顶的异常抛出。"),
	CHECKCAST((byte) 0xc0, InstructionEnum.SHORT_INSTRUCTION, "checkcast", "检验类型转换，检验未通过将抛出ClassCastException。"),
	INSTANCEOF((byte) 0xc1, InstructionEnum.SHORT_INSTRUCTION, "instanceof", "检验对象是否是指定的类的实例，如果是将1压入栈顶，否则将0压入栈"),
	MONITORENTER((byte) 0xc2, "monitorenter", "获得对象的monitor，用于同步方法或同步块。"),
	MONITOREXIT((byte) 0xc3, "monitorexit", "释放对象的monitor，用于同步方法或同步块。"),
	WIDE((byte) 0xc4, "wide", "扩展访问局部变量表的索引宽度。"),
	MULTIANEWARRAY((byte) 0xc5, InstructionEnum.MULTIANEWARRAY_INSTRUCTION, "multianewarray", "创建指定类型和指定维度的多维数组（执行该指令时，操作栈中必须包含各维度的长度值），并将其引用值压入栈顶。"),
	IFNULL((byte) 0xc6, InstructionEnum.BRANCH_INSTRUCTION, "ifnull", "为null时跳转。"),
	IFNONNULL((byte) 0xc7, InstructionEnum.BRANCH_INSTRUCTION, "ifnonnull", "不为null时跳转。"),
	GOTO_W((byte) 0xc8, InstructionEnum.INT_INSTRUCTION, "goto_w", "无条件跳转（宽索引）。"),
	JSR_W((byte) 0xc9, InstructionEnum.INT_INSTRUCTION, "jsr_w", "跳转至指定32位地址偏移量位置，并将jsr_w下一条指令地址压入栈顶。"),
	BREAKPOINT((byte) 0xca, "breakpoint", "调试时的断点标志。"),
	IMPDEP1((byte) 0xfe, "impdep1", "用于在特定硬件中使用的语言后门。"),
	IMPDEP2((byte) 0xff, "impdep2", "用于在特定硬件中使用的语言后门。");



	protected byte opcode;// 操作码
	protected String mnemonicCode;// 助记码
	protected String desc;// 操作码描述
	protected InstructionEnum instructionEnum;// 操作指令类型

	private OpcodeEnum(byte opcode, String mnemonicCode, String desc) {
		this.opcode = opcode;
		this.mnemonicCode = mnemonicCode;
		this.desc = desc;
		this.instructionEnum = InstructionEnum.SIMPLE_INSTRUCTION;
	}

	private OpcodeEnum(byte opcode, InstructionEnum instructionEnum, String mnemonicCode, String desc) {
		this.opcode = opcode;
		this.mnemonicCode = mnemonicCode;
		this.desc = desc;
		this.instructionEnum = instructionEnum;
	}

	// 预加载所有类型，可以使得检索更方便
	private static Map<Byte, OpcodeEnum> typeMap = new HashMap<Byte, OpcodeEnum>();

	static {
		// 预加载所有类型，可以使得检索更快
		for (OpcodeEnum opcodeEnum : OpcodeEnum.values()) {
			typeMap.put(opcodeEnum.opcode, opcodeEnum);
		}
	}
	
	public static OpcodeEnum valueOf(byte code) {
		return typeMap.get(code);
	}

	public InstructionEnum getInstructionEnum() {
		return instructionEnum;
	}

	public final byte getOpcode() {
		return opcode;
	}

	public final String getMnemonicCode() {
		return mnemonicCode;
	}

	public final String getDesc() {
		return desc;
	}
	
}
