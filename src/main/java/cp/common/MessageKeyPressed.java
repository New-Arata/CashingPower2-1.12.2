package cp.common;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageKeyPressed implements IMessage
{
	  public int key;
	  public int target;

	      public MessageKeyPressed(){}

	      public MessageKeyPressed(int i)
	      {
	          this.key = i;
	      }

	      public MessageKeyPressed(int i, int entity)
	      {
	          this.key = i;
	          this.target = entity;
	      }

		@Override
		public void fromBytes(ByteBuf buf) {
			this.key = buf.readByte();
			this.target = buf.readInt();

		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeByte(this.key);
			buf.writeInt(this.target);

		}

}
