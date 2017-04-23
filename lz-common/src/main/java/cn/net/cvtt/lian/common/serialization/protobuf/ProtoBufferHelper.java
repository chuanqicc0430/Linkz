package cn.net.cvtt.lian.common.serialization.protobuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtoBufferHelper {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("08,05,32,B3,01,08,9C,CE,D7,5F,12,2A,73,69,70,3A");
		sbBuffer.append(",32,35,30,33,39,36,37,36,31,40,66,65,6D,6F,6F,2E");
		sbBuffer.append(",61,6D,69,67,6F,2E,62,6A,6D,63,63,2E,6E,65,74,3B");
		sbBuffer.append(",70,3D,34,31,32,33,19,B0,25,A0,29,06,B2,2F,00,20");
		sbBuffer.append(",C8,01,2A,2B,73,69,70,3A,50,47,33,30,31,31,30,38");
		sbBuffer.append(",39,30,40,66,65,6D,6F,6F,2E,61,6D,69,67,6F,2E,62");
		sbBuffer.append(",6A,6D,63,63,2E,6E,65,74,3B,70,3D,34,31,32,33,32");
		sbBuffer.append(",0A,50,53,54,6F,6F,6C,2D,33,30,33,42,3B,0A,2A,73");
		sbBuffer.append(",69,70,3A,32,35,30,33,39,36,37,36,30,40,66,65,6D");
		sbBuffer.append(",6F,6F,2E,61,6D,69,67,6F,2E,62,6A,6D,63,63,2E,6E");
		sbBuffer.append(",65,74,3B,70,3D,34,31,32,33,12,0A,50,53,74,6F,6F");
		sbBuffer.append(",6C,2D,33,30,32,18,B6,03");
		// sbBuffer.append("10, 18, 108, 105, 110, 105, 110, 103, 45, 65, 115, 112, 105, 114, 101, 45, 52, 55, 53, 48, 18, 8, 49, 46, 49, 46, 49, 48, 49, 48, 25, -124, 83, -119, -103, 56, 1, 0, 0, 32, -76, -108, 1");

		args = new String[] { "decode", sbBuffer.toString() };
		process(args);
	}

	/**
	 * 序列化或反序列化处理逻辑
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void process(String args[]) throws Exception {
		String opt = args[0].trim();
		if (opt.equalsIgnoreCase("encode")) {
			int fieldNumber = Integer.valueOf(args[1]);
			int fieldType = Integer.valueOf(args[2]);
			String fieldValue = args[3];
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(output);
			FieldType.valueOf(fieldType).write(codedOutputStream, fieldNumber, fieldValue);
			codedOutputStream.flush();
			System.out.println(toHexString(output.toByteArray()));
		} else if (opt.equalsIgnoreCase("decode")) {
			byte[] buffer = toByteByHex(args[1]);
			CodedInputStream codedInputStream = CodedInputStream.newInstance(buffer);
			List<ProtoBean> protoBeans = print(codedInputStream);
			for (ProtoBean protoBean : protoBeans) {
				System.out.print(protoBean.toString(""));
			}
		}

	}

	/**
	 * 读取一个序列化
	 * 
	 * @param codedInputStream
	 * @return
	 */
	public static List<ProtoBean> print(CodedInputStream input) throws IOException {
		List<ProtoBean> protoBeans = new ArrayList<ProtoBean>();
		int tag;
		while ((tag = input.readTag()) != 0) {
			final int number = WireFormat.getTagFieldNumber(tag);
			switch (WireFormat.getTagWireType(tag)) {
			case WireFormat.WIRETYPE_VARINT:
				protoBeans.add(new ProtoBean(number, "VARINT", input.readInt64() + ""));
				break;
			case WireFormat.WIRETYPE_FIXED64:
				protoBeans.add(new ProtoBean(number, "FIXED64", input.readFixed64() + ""));
				break;
			case WireFormat.WIRETYPE_LENGTH_DELIMITED:
				byte[] buffer = input.readBytes().toByteArray();
				protoBeans.add(new ProtoBean(number, "Object", buffer));
				break;
			case WireFormat.WIRETYPE_START_GROUP:
				// 此种类型消息直接丢弃
				input.skipMessage();
				break;
			case WireFormat.WIRETYPE_END_GROUP:
				input.skipMessage();
				break;
			case WireFormat.WIRETYPE_FIXED32:
				protoBeans.add(new ProtoBean(number, "FIXED32", input.readFixed32() + ""));
				break;
			default:
				throw InvalidProtocolBufferException.invalidWireType();
			}
		}
		return protoBeans;
	}

	/**
	 * 读取一个序列化
	 * 
	 * @param codedInputStream
	 * @return
	 */
	public static List<Node> decode(CodedInputStream input) throws IOException {
		List<Node> nodes = new ArrayList<Node>();
		int tag;
		while ((tag = input.readTag()) != 0) {
			final int number = WireFormat.getTagFieldNumber(tag);
			switch (WireFormat.getTagWireType(tag)) {
			case WireFormat.WIRETYPE_VARINT:
				nodes.add(Node.createLeafNode("[" + number + "] VARINT", input.readInt64() + ""));
				break;
			case WireFormat.WIRETYPE_FIXED64:
				nodes.add(Node.createLeafNode("[" + number + "] FIXED64", input.readFixed64() + ""));
				break;
			case WireFormat.WIRETYPE_LENGTH_DELIMITED:
				byte[] buffer = input.readBytes().toByteArray();
				try {
					List<Node> nodesTemp = decode(CodedInputStream.newInstance(buffer));
					nodes.add(Node.createDirNode("[" + number + "] Object", nodesTemp));
				} catch (Exception e) {
					nodes.add(Node.createLeafNode("[" + number + "] Object", "String:\r\n" + new String(buffer)
							+ "\r\nByte:\r\n" + toHexString(buffer)));
				}
				break;
			case WireFormat.WIRETYPE_START_GROUP:
				// 此种类型消息直接丢弃
				input.skipMessage();
				break;
			case WireFormat.WIRETYPE_END_GROUP:
				input.skipMessage();
				break;
			case WireFormat.WIRETYPE_FIXED32:
				nodes.add(Node.createLeafNode("[" + number + "] FIXED32", input.readFixed32() + ""));
				break;
			default:
				throw InvalidProtocolBufferException.invalidWireType();
			}
		}
		return nodes;
	}

	public static String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append("0x").append(toHexString(b[i]).toUpperCase());
			sb.append(",");
		}
		return sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1).toString() : sb.toString();
	}

	public static String toHexString(byte b) {
		String hex = Integer.toHexString(b & 0xFF).toUpperCase();
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex;
	}

	public static byte[] toByteByHex(String value) {
		value = value != null ? value : "";
		value = value.replaceAll("[^0-9a-zA-Z]", "");
		byte[] buffer = null;
		// 有分号的16进制
		if (value.length() > 2 && value.indexOf(",") > 0) {
			buffer = new byte[value.split(",").length];
			int pos = 0;
			for (String str : value.split(",")) {
				str = str.trim().replace("0x", "");
				str = str.replace("0x", "");
				str = str.replace("0X", "");
				buffer[pos++] = (byte) Integer.parseInt(str, 16);
			}
		} else {
			// 无分号的十六进制
			buffer = new byte[value.length() / 2];
			int pos = 0;
			int index = 0;
			while (value.length() > pos + 1) {
				String str = value.substring(pos, pos + 2);
				str = str.trim().replace("0x", "");
				str = str.replace("0x", "");
				str = str.replace("0X", "");
				buffer[index++] = (byte) Integer.parseInt(str, 16);
				pos += 2;
			}
		}
		return buffer;
	}

	public static byte[] toByteBy10(String value) {
		byte[] buffer = null;
		// 有分号的10进制
		if (value.length() > 2 && value.indexOf(",") > 0) {
			buffer = new byte[value.split(",").length];
			int pos = 0;
			for (String str : value.split(",")) {
				buffer[pos++] = (byte) Integer.parseInt(str.trim(), 10);
			}
		} else {
			// 无分号的10进制
			buffer = new byte[value.length() / 2];
			int pos = 0;
			int index = 0;
			while (value.length() > pos + 1) {
				String str = value.substring(pos, pos + 2);
				buffer[index++] = (byte) Integer.parseInt(str.trim(), 10);
				pos += 2;
			}
		}

		return buffer;
	}

	private static enum FieldType {
		INTEGER(1) {
			@Override
			public void write(CodedOutputStream codedOutputStream, int fieldNumber, String value) throws IOException {
				codedOutputStream.writeInt32(fieldNumber, Integer.valueOf(value));
			}
		},
		LONG(2) {
			@Override
			public void write(CodedOutputStream codedOutputStream, int fieldNumber, String value) throws IOException {
				codedOutputStream.writeInt64(fieldNumber, Long.valueOf(value));
			}
		},
		FLOAT(3) {
			@Override
			public void write(CodedOutputStream codedOutputStream, int fieldNumber, String value) throws IOException {
				codedOutputStream.writeFloat(fieldNumber, Float.valueOf(value));
			}
		},
		DOUBLE(4) {
			@Override
			public void write(CodedOutputStream codedOutputStream, int fieldNumber, String value) throws IOException {
				codedOutputStream.writeDouble(fieldNumber, Double.valueOf(value));
			}
		},
		BOOLEAN(5) {
			@Override
			public void write(CodedOutputStream codedOutputStream, int fieldNumber, String value) throws IOException {
				codedOutputStream.writeBool(fieldNumber, value.equalsIgnoreCase("true"));
			}
		},
		ENUM(6) {
			@Override
			public void write(CodedOutputStream codedOutputStream, int fieldNumber, String value) throws IOException {
				codedOutputStream.writeEnum(fieldNumber, Integer.valueOf(value));
			}
		},
		BYTE(7) {
			@Override
			public void write(CodedOutputStream codedOutputStream, int fieldNumber, String value) throws IOException {
				byte[] buffer = toByteByHex(value);
				codedOutputStream.writeBytes(fieldNumber, ByteString.copyFrom(buffer));
			}
		},
		OBJECT(8) {
			@Override
			public void write(CodedOutputStream codedOutputStream, int fieldNumber, String value) throws IOException {
				codedOutputStream.writeBytes(fieldNumber, ByteString.copyFrom(value.getBytes()));
			}
		};
		int typeValue;
		private static Map<Integer, FieldType> typeMap = new HashMap<Integer, FieldType>();

		static {
			// 预加载所有类型，可以使得检索更快
			for (FieldType fieldType : FieldType.values()) {
				typeMap.put(fieldType.intValue(), fieldType);
			}
		}

		FieldType(int typeValue) {
			this.typeValue = typeValue;
		}

		int intValue() {
			return typeValue;
		}

		public static FieldType valueOf(int type) {
			return typeMap.get(type);
		}

		public abstract void write(CodedOutputStream codedOutputStream, int fieldNumber, String value)
				throws IOException;
	}
}

class ProtoBean {
	private int fieldNumber;
	private String fieldType;
	private byte[] buffer;
	private String fieldValue;

	public ProtoBean(int fieldNumber, String fieldType, String fieldValue) {
		this.buffer = null;
		this.fieldNumber = fieldNumber;
		this.fieldType = fieldType;
		this.fieldValue = fieldValue;
	}

	public ProtoBean(int fieldNumber, String fieldType, byte[] buffer) {
		this.fieldNumber = fieldNumber;
		this.fieldType = fieldType;
		this.buffer = buffer;
	}

	public String toString(String space) {
		StringBuffer sb = new StringBuffer();
		sb.append(fieldNumber).append("|");
		sb.append(fieldType).append("|");
		if (buffer != null && fieldValue == null) {
			try {
				CodedInputStream codedInputStream = CodedInputStream.newInstance(buffer);
				List<ProtoBean> protoBeans = ProtoBufferHelper.print(codedInputStream);
				sb.append("\r\n");
				for (ProtoBean protoBean : protoBeans) {
					sb.append(space).append(" |-- ").append(protoBean.toString(space + "     "));
				}
			} catch (Exception e) {
				sb.append(new String(buffer));
			}
		} else {
			sb.append(fieldValue);
		}
		sb.append("\r\n");
		return sb.toString();
	}
}
