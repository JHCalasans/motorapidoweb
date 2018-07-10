package br.com.motorapido.util.ws.retornos;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message implements Serializable {

	private static final long serialVersionUID = -440479733387277189L;

	private String Text;

	private Date MessageDateTime;

	private boolean IsTextIn;

	public Message() {

	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public Date getMessageDateTime() {
		return MessageDateTime;
	}

	public void setMessageDateTime(Date messageDateTime) {
		MessageDateTime = messageDateTime;
	}

	public boolean isIsTextIn() {
		return IsTextIn;
	}

	public void setIsTextIn(boolean isTextIn) {
		IsTextIn = isTextIn;
	}

}
