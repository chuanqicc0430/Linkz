// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// http://code.google.com/p/protobuf/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package cn.net.cvtt.lian.common.serialization.protobuf;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoConfig;
import cn.net.cvtt.lian.common.serialization.protobuf.util.FileUtil;

/**
 * Thrown when a protocol message being parsed is invalid in some way, e.g. it
 * contains a malformed varint or a negative byte length.
 * 
 * @author kenton@google.com Kenton Varda
 */
public class InvalidProtocolBufferException extends IOException {
	private static final long serialVersionUID = -1616151763072450476L;

	public InvalidProtocolBufferException(final String description) {
		super((ProtoManager.isDebug() ? getProtoBuilderStackString() : "") + description);
	}

	public static InvalidProtocolBufferException truncatedMessage() {
		return new InvalidProtocolBufferException("While parsing a protocol message, the input ended unexpectedly "
				+ "in the middle of a field.  This could mean either than the "
				+ "input has been truncated or that an embedded message " + "misreported its own length.");
	}

	public static InvalidProtocolBufferException negativeSize() {
		return new InvalidProtocolBufferException("CodedInputStream encountered an embedded string or message "
				+ "which claimed to have negative size.");
	}

	public static InvalidProtocolBufferException malformedVarint() {
		return new InvalidProtocolBufferException("CodedInputStream encountered a malformed varint.");
	}

	public static InvalidProtocolBufferException invalidTag() {
		return new InvalidProtocolBufferException("Protocol message contained an invalid tag (zero).");
	}

	public static InvalidProtocolBufferException invalidEndTag() {
		return new InvalidProtocolBufferException("Protocol message end-group tag did not match expected tag.");
	}

	public static InvalidProtocolBufferException invalidWireType() {
		return new InvalidProtocolBufferException("Protocol message tag had invalid wire type.");
	}

	public static InvalidProtocolBufferException recursionLimitExceeded() {
		return new InvalidProtocolBufferException(
				"Protocol message had too many levels of nesting.  May be malicious.  "
						+ "Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
	}

	public static InvalidProtocolBufferException sizeLimitExceeded() {
		return new InvalidProtocolBufferException("Protocol message was too large.  May be malicious.  "
				+ "Use CodedInputStream.setSizeLimit() to increase the size limit.");
	}

	/** 默认仅寻找64层以内的栈调用信息 */
	private static final int DEFAULT_STACK_DEPTH_LIMIT = 64;

	/** 获得当前线程中在ProtoBuilder调用栈信息 */
	private static StackTraceElement getProtoBuilderStack() {
		StackTraceElement result = null;
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			StackTraceElement stackTemp = stack[i];
			if (getFileName(stackTemp).endsWith(ProtoConfig.PROTO_BUILDER_NAME + ".java")
					&& !getFileName(stackTemp).equals(ProtoConfig.PROTO_BUILDER_NAME + ".java")) {
				result = stackTemp;
				break;
			} else if (i > DEFAULT_STACK_DEPTH_LIMIT) {
				break;
			}
		}
		return result;
	}

	private static String getProtoBuilderStackString() {
		StackTraceElement stack = getProtoBuilderStack();
		StringBuilder sb = new StringBuilder();
		if (stack != null) {
			sb.append("Calling ").append(stack.getClassName()).append(".").append(stack.getMethodName())
					.append("() method is error!Error location:\n");
			String sourceString = FileUtil.readLine(ProtoManager.SOURCE_SAVE_PATH + getFileName(stack),
					stack.getLineNumber());
			sb.append(sourceString).append("\n");
		}
		return sb.toString();
	}

	private static String getFileName(StackTraceElement stack) {
		String fileName = stack.getFileName();
		if (fileName != null && fileName.endsWith("from JavaSourceFromString")) {
			fileName = fileName.replaceAll("from JavaSourceFromString", "");
			return fileName.trim();
		}
		return "";
	}
}
