/*
 * Copyright (c) 2014 acmi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package acmi.l2.clientmod.unreal.bytecode;

public class BytecodeToken {
    private String text;

    public BytecodeToken(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static class ErrToken extends BytecodeToken {
        public ErrToken(String text) {
            super(text);
        }
    }

    public static class ReturnToken extends BytecodeToken {
        private BytecodeToken returnValue;

        public ReturnToken(BytecodeToken returnValue) {
            super(("return " + returnValue).trim());
            this.returnValue = returnValue;
        }

        public BytecodeToken getReturnValue() {
            return returnValue;
        }
    }

    public static class NothingToken extends BytecodeToken {
        public NothingToken() {
            super("");
        }
    }

    public static abstract class JumpToken extends BytecodeToken {
        private int targetOffset;

        protected JumpToken(String text, int targetOffset) {
            super(text);
            this.targetOffset = targetOffset;
        }
    }

    public static class UncondJumpToken extends JumpToken {
        public UncondJumpToken(int targetOffset) {
            super("jump " + targetOffset, targetOffset);
        }
    }

    public static class JumpIfNotToken extends JumpToken {
        public JumpIfNotToken(int targetOffset, BytecodeToken condition) {
            super("if (!" + condition + ") jump " + targetOffset, targetOffset);
        }
    }

    public static class EndParmsToken extends BytecodeToken {
        public EndParmsToken(String text) {
            super(text);
        }
    }

    public static class DefaultValueToken extends BytecodeToken {
        public DefaultValueToken(String text) {
            super(text);
        }
    }

    public static class ForeachToken extends JumpToken {
        private BytecodeToken expr;
        private BytecodeToken iteratorExpr;

        public ForeachToken(int targetOffset, BytecodeToken expr, BytecodeToken iteratorExpr) {
            super("foreach (" + expr + ") end " + targetOffset, targetOffset);
            this.expr = expr;
            this.iteratorExpr = iteratorExpr;
        }

        public ForeachToken(int targetOffset, BytecodeToken expr) {
            this(targetOffset, expr, null);
        }

        public BytecodeToken getExpr() {
            return expr;
        }

        public BytecodeToken getIteratorExpr() {
            return iteratorExpr;
        }
    }

    public static class IteratorPopToken extends BytecodeToken {
        public IteratorPopToken() {
            super("IteratorPop");
        }
    }

    public static class IteratorNextToken extends BytecodeToken {
        public IteratorNextToken() {
            super("IteratorNext");
        }
    }
}