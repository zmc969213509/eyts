package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * 常见疾病实体类
 * jnluo，jnluo5889@126.com
 */
public class CommonDisData {


    /**
     * bio : 小儿营养不良，医学上是指由于摄食不足或食物不能充分吸收利用，以致能量缺乏，不能维持正常代谢，迫使肌体消耗，表现为体重减轻或不增，生长发育停滞。
     * cure : 1. 定期检查：定期检查孩子各项生长发育指标，如身高、体重、乳牙数目等，早期发现小儿在生长发育上的偏离，尽早加以矫治；
     * 2. 饮食调节：保证睡眠充足，培养良好的饮食习惯，防止挑食、偏食，不要过多地吃零食，饮食调节是治疗营养不良的基本措施。轻中度营养不良以调节饮食为主，给以高蛋白、高热量饮食，同时 要保证营养均衡，必要时补充微营养素制剂；
     * 3. 纠正不良饮食行为：家长不要在孩子面前谈论某种食物好吃或不好吃，不做偏食的榜样，鼓励孩子认识各种各样的营养食物，了解它们的来源，制作加工的方法，注意饮食的多样化；吃饭时周围气氛轻松愉快，父母不要强迫孩子吃多少饭菜，应该根据孩子自己的量来决定吃多少；当孩子对过去不喜欢的食物偶尔表现出吃的香甜时，家长应立即给予表扬，以达到强化的目的；进餐时，故事书、玩具、电视、哄劝等都要退避三舍，否则将分散小儿的注意力，影响消化机能，并容易形成要吃饭就得讲故事、就得看电视的习惯，这样一来，吃饭时注意力不集中又影响食欲，食欲减退就更不愿进食的恶性循环就产生了；
     * 让孩子体验“饥饿”，养成定时进食的好习惯；
     * disid : 7
     * docname : 杜亚松
     * doctoranswerquestion : 你的宝宝身长85厘米，体重8.80公斤，体重/身长测量值低于中位数减2个标准差，属于营养不良范围里的中度消瘦。
     * doctoricon : http://ohcbtrn97.bkt.clouddn.com/a6f4607c8dd3328632543755166b4a0f231f849841286-V3BWM3_fw658_18.png
     * doctorid : 4
     * doctorname : 学水蓝
     * iscommon : 1
     * name : 营养不良
     * prompt : 经常带小儿到户外，利用天然条件，呼吸新鲜空气，多晒太阳，常开展户外活动及体育锻炼，增强体质，通过适当的体格锻炼增强体质。
     * secname : 儿童保健
     * sectionid : 10
     * symptom : 小儿营养不良，医学上是指由于摄食不足或食物不能充分吸收利用，以致能量缺乏，不能维持正常代谢，迫使肌体消耗，表现为体重减轻或不增，生长发育停滞。
     * 营养不良是热量和/或蛋白质不足而致的慢性营养缺乏症，又称蛋白能量不足性营养不良，一般多见于3岁以下的幼儿。
     * 小儿营养不良的一般症状表现为发育迟缓、身材矮小、皮肤毛发无光泽、黏膜苍白、体重不增加甚至减少、贫血等，营养不良的患儿免疫功能低下，易患各种感染，还可能对智力发育造成难以恢复的影响。
     * 小孩营养不良有三个大的原因：第一，摄入不足；第二，消耗过多；第三，吸收不良，但食物在体内的消化、吸收和转化，不仅仅是单一的营养素作用，还有营养行为和营养气氛，同样影响的营养结局，所以，如果喂养方法和方式不对的话，一样会造成儿童营养不良、生长发育落后或者迟缓。
     * usericon : http://ohc9vxp1g.bkt.clouddn.com/%E6%9C%AA%E6%A0%87%E9%A2%98-1_10.png
     * username : 稽津童
     * userputquestion : 男宝宝两岁了，前几天去体检，身长85厘米，体重8.80公斤，医生说孩子营养不良，孩子平时吃饭挑食，每天追着喂，怎么办呢？
     */

    private String bio;
    private String cure;
    private String disid;
    private String docname;
    private String doctoranswerquestion;
    private String doctoricon;
    private String doctorid;
    private String doctorname;
    private int iscommon;  //1表示 常见疾病，0 表示普通疾病
    private String name;   //疾病名称
    private String prompt;
    private String secname; //科室
    private String sectionid;
    private String symptom;
    private String usericon;
    private String username;
    private String userputquestion;

    public CommonDisData(String userputquestion, String bio, String cure, String disid, String docname, String doctoranswerquestion, String doctoricon, String doctorid, String doctorname, int iscommon, String name, String prompt, String secname, String sectionid, String symptom, String usericon, String username) {
        this.userputquestion = userputquestion;
        this.bio = bio;
        this.cure = cure;
        this.disid = disid;
        this.docname = docname;
        this.doctoranswerquestion = doctoranswerquestion;
        this.doctoricon = doctoricon;
        this.doctorid = doctorid;
        this.doctorname = doctorname;
        this.iscommon = iscommon;
        this.name = name;
        this.prompt = prompt;
        this.secname = secname;
        this.sectionid = sectionid;
        this.symptom = symptom;
        this.usericon = usericon;
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCure() {
        return cure;
    }

    public void setCure(String cure) {
        this.cure = cure;
    }

    public String getDisid() {
        return disid;
    }

    public void setDisid(String disid) {
        this.disid = disid;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDoctoranswerquestion() {
        return doctoranswerquestion;
    }

    public void setDoctoranswerquestion(String doctoranswerquestion) {
        this.doctoranswerquestion = doctoranswerquestion;
    }

    public String getDoctoricon() {
        return doctoricon;
    }

    public void setDoctoricon(String doctoricon) {
        this.doctoricon = doctoricon;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public int getIscommon() {
        return iscommon;
    }

    public void setIscommon(int iscommon) {
        this.iscommon = iscommon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getSecname() {
        return secname;
    }

    public void setSecname(String secname) {
        this.secname = secname;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserputquestion() {
        return userputquestion;
    }

    public void setUserputquestion(String userputquestion) {
        this.userputquestion = userputquestion;
    }

    @Override
    public String toString() {
        return "CommonDisData{" +
                "bio='" + bio + '\'' +
                ", cure='" + cure + '\'' +
                ", disid='" + disid + '\'' +
                ", docname='" + docname + '\'' +
                ", doctoranswerquestion='" + doctoranswerquestion + '\'' +
                ", doctoricon='" + doctoricon + '\'' +
                ", doctorid='" + doctorid + '\'' +
                ", doctorname='" + doctorname + '\'' +
                ", iscommon=" + iscommon +
                ", name='" + name + '\'' +
                ", prompt='" + prompt + '\'' +
                ", secname='" + secname + '\'' +
                ", sectionid='" + sectionid + '\'' +
                ", symptom='" + symptom + '\'' +
                ", usericon='" + usericon + '\'' +
                ", username='" + username + '\'' +
                ", userputquestion='" + userputquestion + '\'' +
                '}';
    }
}
