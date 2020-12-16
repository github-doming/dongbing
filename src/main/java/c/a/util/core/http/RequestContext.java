package c.a.util.core.http;

/**
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class RequestContext {

	private String pathDomain = null;
	private String pathReal = null;
	private String pathDomainPic = null;
	private String pathRealPic = null;

	public String getPathDomain() {
		return pathDomain;
	}

	public void setPathDomain(String pathDomain) {
		this.pathDomain = pathDomain;
	}

	public String getPathReal() {
		return pathReal;
	}

	public void setPathReal(String pathReal) {
		this.pathReal = pathReal;
	}

	public String getPathDomainPic() {
		return pathDomainPic;
	}

	public void setPathDomainPic(String pathDomainPic) {
		this.pathDomainPic = pathDomainPic;
	}

	public String getPathRealPic() {
		return pathRealPic;
	}

	public void setPathRealPic(String pathRealPic) {
		this.pathRealPic = pathRealPic;
	}

}
