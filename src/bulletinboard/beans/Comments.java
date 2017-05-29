package bulletinboard.beans;
import java.io.Serializable;
import java.util.Date;
public class Comments  implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String body;
	private String message_id;
	private String user_id;
	private Date insertDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
