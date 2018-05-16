package br.com.motorapido.mbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@SuppressWarnings("deprecation")
@ManagedBean(name = "uploadedFileBean")
@SessionScoped
public class UploadedFileBean implements Serializable {

	private static final long serialVersionUID = -4025821132822474549L;
	transient private UploadedFile file;

	public UploadedFileBean() {
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void fileUploadAction(FileUploadEvent event) {
		setFile(event.getFile());
	}
	
	public String getFileName()
	{
		if(file == null)
			return "";
		else
		    return this.file.getFileName();
	}
	
	public void setFileNull()
	{
		setFile(null);
	}
}
