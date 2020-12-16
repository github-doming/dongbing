package c.x.platform.root.compo.tree_table.bean;
public class TreeTableNodeEventBean {
	private String id;
	private String name;
	private String sequence;
	private String parent_id;
	private String url;
	private String pic;
	private String pic_open;
	private String pic_close;
	/**
	 * 是否叶子
	 */
	private boolean leaf;
	/**
	 * 深度(显示深度，不显示根的线条图片)
	 */
	private int show_layer;
	/**
	 * 深度(真正深度，第几层)
	 */
	private int layer;
	/**
	 * 是否最后子节点
	 */
	private boolean last_leaf;
	/**
	 * 搜索path
	 */
	private String path;
	/**
	 * treeCode
	 */
	private String tree_code;
	// -- set/get --//
	// -- { --//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parentId) {
		parent_id = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPic_open() {
		return pic_open;
	}
	public void setPic_open(String picOpen) {
		pic_open = picOpen;
	}
	public String getPic_close() {
		return pic_close;
	}
	public void setPic_close(String picClose) {
		pic_close = picClose;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public int getShow_layer() {
		return show_layer;
	}
	public void setShow_layer(int layer) {
		this.show_layer = layer;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public boolean isLast_leaf() {
		return last_leaf;
	}
	public void setLast_leaf(boolean lastLeaf) {
		last_leaf = lastLeaf;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTree_code() {
		return tree_code;
	}
	public void setTree_code(String treeCode) {
		tree_code = treeCode;
	}
	// -- } --//
	// -- set/get --//
}