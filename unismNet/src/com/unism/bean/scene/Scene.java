package com.unism.bean.scene;

// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Scene entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "op_scene", catalog = "unismweb")
public class Scene implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8493505762920576464L;
	// Fields
	/**
	 * ID,uuid,主键，唯一
	 */
	private String sceneId = null;
	/**
	 * 场景所在地
	 */
	private String sceneAddr;
	/**
	 * 场景添加时间
	 */
	private Timestamp sceneCreateDate;
	/**
	 * 创建者
	 */
	private String sceneCreater;
	/**
	 * 场景类型子类
	 * <ul>
	 * <li>11： 设施蔬菜</li>
	 * <li>12: 设施花卉</li>
	 * <li>21：池塘水产养殖</li>
	 * <li>22：设施水产养殖</li>
	 * <li>31：井灌</li>
	 * <li>32：低压管灌</li>
	 * <li>33：明渠</li>
	 * <li>34：田间滴灌</li>
	 * </ul>
	 */
	private Integer sceneCtype;
	/**
	 * 场景的说明
	 */
	private String sceneDesc;
	/**
	 * 场景类型细类
	 * <ul>
	 * <li>1: 蟹池塘</li>
	 * <li>2：池塘组</li>
	 * <li>3：基地</li>
	 * <li>4：企业</li>
	 * <li>5：项目</li>
	 * <li>101：日光温室</li>
	 * <li>102：连栋温室</li>
	 * <li>201：养殖虾池塘</li>
	 * <li>202：鱼池塘</li>
	 * <li>203：育苗虾池塘</li>
	 * <li>301：水分监测站</li>
	 * <li>302：水文监测站</li>
	 * <li>303：水质监测站</li>
	 * <li>97：视频点</li>
	 * <li>98：气象站</li>
	 * <li>99：移动场景</li>
	 * </ul>
	 */
	private Integer sceneGtype;
	/**
	 * 场景的图片
	 */
	private String sceneImage;
	/**
	 * 关键字
	 */
	private String sceneKeyWord;
	/**
	 * 维度
	 */
	private String sceneLatitude;
	/**
	 * 经度
	 */
	private String sceneLongitude;
	/**
	 * 场景名称
	 */
	private String sceneName;
	/**
	 * 场景编号,唯一编号
	 */
	private String sceneNo;
	/**
	 * 所属场景父ID,父节点,FF是表示空，不在网络中
	 */
	private String scenePid;
	/**
	 * 所属等级
	 */
	private Integer sceneRank;
	/**
	 * 场景使用状态
	 */
	private Integer sceneState;
	/**
	 * 场景类型
	 * <ul>
	 * <li>1 设施园艺</li>
	 * <li>2 水产养殖</li>
	 * <li>3 大田种植</li>
	 * <li>4 畜禽养殖</li>
	 * <ul>
	 */
	private Integer sceneType;
	/**
	 * 定制路径
	 */
	private String sceneUrl;
	/**
	 * 视频点URL
	 */
	private String sceneVideoUrl;
	/**
	 * 区划ID,外键关联Op_Areas的area_id
	 */
	private String areaId;
	/**
	 * 
	 */
	private Integer sceneOrder;

	/**
	 * 场景是否可见
	 */
	private Boolean sceneVisible = true;

	/**
	 * 场景类型子类
	 */
	private Set<Scene> sceneChildType = new HashSet<Scene>();

	/**
	 * 场景类型父类
	 */
	private Scene sceneParentType;

	// Constructors

	/** default constructor */
	public Scene() {
	}

	/** minimal constructor */
	public Scene(String sceneId) {
		this.sceneId = sceneId;
	}

	public Scene(String sceneId, String sceneName) {

		this.sceneId = sceneId;
		this.sceneName = sceneName;
	}

	/** full constructor */
	public Scene(String sceneId, String sceneAddr, Timestamp sceneCreateDate,
			String sceneCreater, Integer sceneCtype, String sceneDesc,
			Integer sceneGtype, String sceneImage, String sceneKeyWord,
			String sceneLatitude, String sceneLongitude, String sceneName,
			String sceneNo, String scenePid, Integer sceneRank,
			Integer sceneState, Integer sceneType, String sceneUrl,
			String sceneVideoUrl, String areaId, Integer sceneOrder) {
		this.sceneId = sceneId;
		this.sceneAddr = sceneAddr;
		this.sceneCreateDate = sceneCreateDate;
		this.sceneCreater = sceneCreater;
		this.sceneCtype = sceneCtype;
		this.sceneDesc = sceneDesc;
		this.sceneGtype = sceneGtype;
		this.sceneImage = sceneImage;
		this.sceneKeyWord = sceneKeyWord;
		this.sceneLatitude = sceneLatitude;
		this.sceneLongitude = sceneLongitude;
		this.sceneName = sceneName;
		this.sceneNo = sceneNo;
		this.scenePid = scenePid;
		this.sceneRank = sceneRank;
		this.sceneState = sceneState;
		this.sceneType = sceneType;
		this.sceneUrl = sceneUrl;
		this.sceneVideoUrl = sceneVideoUrl;
		this.areaId = areaId;
		this.sceneOrder = sceneOrder;
	}

	// Property accessors
	@Id
	@Column(name = "scene_id", unique = true, nullable = false)
	// @GeneratedValue(strategy = GenerationType.AUTO)//该方法与unique冲突
	public String getSceneId() {
		return this.sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	@Column(name = "scene_addr")
	public String getSceneAddr() {
		return this.sceneAddr;
	}

	public void setSceneAddr(String sceneAddr) {
		this.sceneAddr = sceneAddr;
	}

	@Column(name = "scene_createDate", length = 0)
	public Timestamp getSceneCreateDate() {
		return this.sceneCreateDate;
	}

	public void setSceneCreateDate(Timestamp sceneCreateDate) {
		this.sceneCreateDate = sceneCreateDate;
	}

	@Column(name = "scene_creater")
	public String getSceneCreater() {
		return this.sceneCreater;
	}

	public void setSceneCreater(String sceneCreater) {
		this.sceneCreater = sceneCreater;
	}

	@Column(name = "scene_ctype")
	public Integer getSceneCtype() {
		return this.sceneCtype;
	}

	public void setSceneCtype(Integer sceneCtype) {
		this.sceneCtype = sceneCtype;
	}

	@Column(name = "scene_desc")
	public String getSceneDesc() {
		return this.sceneDesc;
	}

	public void setSceneDesc(String sceneDesc) {
		this.sceneDesc = sceneDesc;
	}

	@Column(name = "scene_gtype")
	public Integer getSceneGtype() {
		return this.sceneGtype;
	}

	public void setSceneGtype(Integer sceneGtype) {
		this.sceneGtype = sceneGtype;
	}

	@Column(name = "scene_image")
	public String getSceneImage() {
		return this.sceneImage;
	}

	public void setSceneImage(String sceneImage) {
		this.sceneImage = sceneImage;
	}

	@Column(name = "scene_keyWord")
	public String getSceneKeyWord() {
		return this.sceneKeyWord;
	}

	public void setSceneKeyWord(String sceneKeyWord) {
		this.sceneKeyWord = sceneKeyWord;
	}

	@Column(name = "scene_latitude")
	public String getSceneLatitude() {
		return this.sceneLatitude;
	}

	public void setSceneLatitude(String sceneLatitude) {
		this.sceneLatitude = sceneLatitude;
	}

	@Column(name = "scene_longitude")
	public String getSceneLongitude() {
		return this.sceneLongitude;
	}

	public void setSceneLongitude(String sceneLongitude) {
		this.sceneLongitude = sceneLongitude;
	}

	@Column(name = "scene_name")
	public String getSceneName() {
		return this.sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	@Column(name = "scene_no", unique = true)
	public String getSceneNo() {
		return this.sceneNo;
	}

	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}

	@Column(name = "scene_pid")
	public String getScenePid() {
		return this.scenePid;
	}

	public void setScenePid(String scenePid) {
		this.scenePid = scenePid;
	}

	@Column(name = "scene_rank")
	public Integer getSceneRank() {
		return this.sceneRank;
	}

	public void setSceneRank(Integer sceneRank) {
		this.sceneRank = sceneRank;
	}

	@Column(name = "scene_state")
	public Integer getSceneState() {
		return this.sceneState;
	}

	public void setSceneState(Integer sceneState) {
		this.sceneState = sceneState;
	}

	@Column(name = "scene_type")
	public Integer getSceneType() {
		return this.sceneType;
	}

	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}

	@Column(name = "scene_url")
	public String getSceneUrl() {
		return this.sceneUrl;
	}

	public void setSceneUrl(String sceneUrl) {
		this.sceneUrl = sceneUrl;
	}

	@Column(name = "scene_videoUrl")
	public String getSceneVideoUrl() {
		return this.sceneVideoUrl;
	}

	public void setSceneVideoUrl(String sceneVideoUrl) {
		this.sceneVideoUrl = sceneVideoUrl;
	}

	@Column(name = "area_id")
	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "scene_order")
	public Integer getSceneOrder() {
		return this.sceneOrder;
	}

	public void setSceneOrder(Integer sceneOrder) {
		this.sceneOrder = sceneOrder;
	}

	@Column(nullable = false, name = "scene_visible")
	public Boolean getSceneVisible() {
		return sceneVisible;
	}

	public void setSceneVisible(Boolean sceneVisible) {
		this.sceneVisible = sceneVisible;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE }, mappedBy = "sceneParentType",fetch=FetchType.EAGER)
	public Set<Scene> getSceneChildType() {
		return sceneChildType;
	}

	public void setSceneChildType(Set<Scene> sceneChildType) {
		this.sceneChildType = sceneChildType;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "scene_parentid")
	public Scene getSceneParentType() {
		return sceneParentType;
	}

	public void setSceneParentType(Scene sceneParentType) {
		this.sceneParentType = sceneParentType;
	}
}