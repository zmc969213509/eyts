package com.guojianyiliao.eryitianshi.View.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;

public class UserAgreementActivity extends MyBaseActivity {
    private LinearLayout ll_return;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        findView();
    }

    private void findView() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv= (TextView) findViewById(R.id.tv);
        ll_return.setOnClickListener(listener);
        tv.setText("本协议是您与儿医天使客户端（简称“本客户端”）所有者（以下简称为“儿医天使”）之间就儿医天使客户端服务等相关事宜所订立的契约，请您仔细阅读本注册协议，您点击“注册”按钮后，本协议即构成对双方有约束力的法律文件。\n" +
                "第1条本客户端服务条款的确认和接纳\n" +
                "1.1. 本客户端的各项电子服务的所有权和运作权归儿医天使所有。用户同意所有注册协议条款并完成注册程序，才能成为本客户端的正式用户。用户确认：本协议条款是处理双方权利义务的契约，始终有效，法律另有强制性规定或双方另有特别约定的，依其规定。\n" +
                "1.2. 用户点击同意本协议的，即视为用户确认自己具有享受本客户端服务、下单购买等相应的权利能力和行为能力，能够独立承担法律责任。\n" +
                "1.3. 如果您在18周岁以下，您只能在父母或监护人的监护参与下才能使用本客户端。\n" +
                "1.4. 儿医天使保留在中华人民共和国大陆地区法施行之法律允许的范围内独自决定拒绝服务、关闭用户账户、清除或编辑内容或取消订单的权利。\n" +
                "第2条本客户端服务\n" +
                "2.1. 儿医天使通过互联网依法为用户提供互联网信息等服务，用户在完全同意本协议及本客户端规定的情况下，方有权使用本客户端的相关服务。\n" +
                "2.2. 用户必须自行准备如下设备和承担如下开支：\n" +
                "  （1）上网设备，包括并不限于电脑或者其他上网终端、调制解调器及其他必备的上网装置；\n" +
                "  （2）上网开支，包括并不限于网络接入费、上网设备租用费、手机流量费等。\n" +
                "第3条用户信息\n" +
                "3.1. 用户应自行诚信向本客户端提供注册资料，用户同意其提供的注册资料真实、准确、完整、合法有效，用户注册资料如有变动的，应及时更新其注册资料。如果用户提供的注册资料不合法、不真实、不准确、不详尽的，用户需承担因此引起的相应责任及后果，并且儿医天使保留终止用户使用儿医天使各项服务的权利。\n" +
                "3.2. 用户在本客户端进行购买活动时，涉及用户真实姓名/名称、通信地址、联系电话、电子邮箱等隐私信息的，本站将予以严格保密，除非得到用户的授权或法律另有规定，本站不会向外界披露用户隐私信息。\n" +
                "3.3. 用户注册成功后，将产生用户名和密码等账户信息，您可以根据本客户端规定改变您的密码。用户应谨慎合理的保存、使用其用户名和密码。用户若发现任何非法使用用户账号或存在安全漏洞的情况，请立即通知本客户端并向公安机关报案。\n" +
                "3.4. 用户同意，儿医天使拥有通过邮件、短信电话等形式，向在本客户端注册、购买的用户发送订单信息、促销活动等告知信息的权利。\n" +
                "3.5. 用户不得将在本站注册获得的账户借给他人使用，否则用户应承担由此产生的全部责任，并与实际使用人承担连带责任。\n" +
                "3.6. 用户同意，儿医天使有权使用用户的注册信息、用户名、密码等信息，登陆进入用户的注册账户，进行证据保全，包括但不限于公证、见证等。\n" +
                "第4条用户依法言行义务\n" +
                "本协议依据国家相关法律法规规章制定，用户同意严格遵守以下义务：\n" +
                "  （1）不得传输或发表：煽动抗拒、破坏宪法和法律、行政法规实施的言论，煽动颠覆国家政权，推翻社会主义制度的言论，煽动分裂国家、破坏国家统一的的言论，煽动民族仇恨、民族歧视、破坏民族团结的言论；\n" +
                "  （2）从中国大陆向境外传输资料信息时必须符合中国有关法规；\n" +
                "  （3）不得利用本客户端从事洗钱、窃取商业秘密、窃取个人信息等违法犯罪活动；\n" +
                "  （4）不得干扰本客户端的正常运转，不得侵入本客户端及国家计算机信息系统；\n" +
                "  （5）不得传输或发表任何违法犯罪的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、庸俗的、淫秽的、不文明的等信息资料；\n" +
                "  （6）不得传输或发表损害国家社会公共利益和涉及国家安全的信息资料或言论；\n" +
                "  （7）不得教唆他人从事本条所禁止的行为；\n" +
                "  （8）不得利用在本客户端注册的账户进行牟利性经营活动；\n" +
                "  （9）不得发布任何侵犯他人著作权、商标权等知识产权或合法权利的内容；用户应关注并遵守本客户端不时公布或修改的各类合法规则规定。本客户端保有删除各类不符合法律政策或不真实的信息内容而无须通知用户的权利。若用户未遵守以上规定的，本客户端有权作出独立判断并采取暂停或关闭用户帐号等措施。用户须对自己在网上的言论和行为承担法律责任。\n" +
                "第5条商品信息\n" +
                "本客户端上的服务价格、数量、能否提供等信息随时都有可能发生变动，本客户端不作特别通知。本客户端显示的信息可能会有一定的滞后性或差错，对此情形您知悉并理解；天使儿医欢迎纠错，并会视情况给予纠错者一定的奖励。为表述便利，产品和服务简称为“产品”或“服务”。\n" +
                "第6条订单\n" +
                "6.1. 在您下订单时，请您仔细确认所购服务的名称、价格、数量、说明、注意事项、联系地址、电话、联系人等信息。联系人与用户本人不一致的，联系人的行为和意思表示视为用户的行为和意思表示，用户应对联系人的行为及意思表示的法律后果承担连带责任。\n" +
                "6.2. 除法律另有强制性规定外，双方约定如下：\n" +
                "  （1）本客户端上儿医天使展示的服务和价格等信息仅仅是要约邀请，您下单时须填写您希望购买的服务数量、价款及支付方式、联系人、联系方式、联系地址（合同履行地点）、合同履行方式等内容；\n" +
                "  （2）系统生成的订单信息是计算机信息系统根据您填写的内容自动生成的数据，仅是您向儿医天使发出的合同要约；\n" +
                "  （3）儿医天使收到您的订单信息后，只有在儿医天使将您在订单中订购的服务向您或指定联系人交付时（接受服务为准），方视为您与儿医天使之间就向您或指定联系人提供的服务建立了合同关系；\n" +
                "  （4）如果您在一份订单里订购了多份服务并且儿医天使只给您或指定联系人交付了部分服务时，您与儿医天使之间仅就实际向您或指定联系人提供的服务建立了合同关系；\n" +
                "  （5）只有在儿医天使实际向您或指定联系人提供了订单中订购的其他服务时，您和儿医天使之间就订单中该其他已实际向您或指定联系人提供的服务才成立合同关系；\n" +
                "  （6）您可以随时登陆您在本客户端注册的账户，查询您的订单状态。\n" +
                "6.3. 由于市场变化及各种以合理商业努力难以控制的因素的影响，本客户端无法保证您提交的订单信息中希望购买的服务都能提供；如您拟购买的服务，发生无法提供的情形，您有权取消订单。\n" +
                "6.4. 本客户端仅为用户提供中文咨询及书面服务，若用户有英文服务需求，请致电本站客户服务部另行协商确定服务价格。\n" +
                "第7条配送\n" +
                "7.1. 儿医天使将会把产品（服务）发送到您或指定联系人所指定的接收端或邮箱等，所有在本客户端列出的交付时间为参考时间，参考时间的计算是根据具体服务的处理过程和发送时间的基础上估计得出的。\n" +
                "7.2. 因如下情况造成订单延迟或无法交付等，儿医天使不承担延迟交付的责任：\n" +
                "  （1）用户提供的信息错误、接受端设备等原因导致的；\n" +
                "  （2）服务发送后无人查阅的；\n" +
                "  （3）情势变更因素导致的；\n" +
                "  （4）不可抗力因素导致的，例如：自然灾害、基础网络问题、突发战争、网络黑客等。\n" +
                "第8条所有权及知识产权条款\n" +
                "8.1. 用户一旦接受本协议，即表明该用户主动将其在任何时间段在本客户端发表的任何形式的信息内容（包括但不限于客户评价、客户咨询、各类话题文章等信息内容）的财产性权利等任何可转让的权利，如著作权财产权（包括并不限于：复制权、发行权、出租权、展览权、表演权、放映权、广播权、信息网络传播权、摄制权、改编权、翻译权、汇编权以及应当由著作权人享有的其他可转让权利），全部独家且不可撤销地转让给儿医天使所有，用户同意儿医天使有权就任何主体侵权而单独提起诉讼。\n" +
                "8.2. 本协议已经构成《中华人民共和国著作权法》第二十五条（条文序号依照2011年版著作权法确定）及相关法律规定的著作财产权等权利转让书面协议，其效力及于用户在儿医天使客户端上发布的任何受著作权法保护的作品内容，无论该等内容形成于本协议订立前还是本协议订立后。\n" +
                "8.3. 用户同意并已充分了解本协议的条款，承诺不将已发表于本客户端的信息，以任何形式发布或授权其它主体以任何方式使用（包括但限于在各类网站、媒体上使用）。\n" +
                "8.4. 儿医天使是本客户端的制作者，拥有此客户端内容及资源的著作权等合法权利，受国家法律保护，有权不时地对本协议及本客户端的内容进行修改，并在本客户端张贴，无须另行通知用户。在法律允许的最大限度范围内，儿医天使对本协议及本客户端内容拥有解释权。\n" +
                "8.5. 除法律另有强制性规定外，未经儿医天使明确的特别书面许可，任何单位或个人不得以任何方式非法地全部或部分复制、转载、引用、链接、抓取或以其他方式使用本客户端的信息内容，否则，儿医天使有权追究其法律责任。\n" +
                "8.6. 本客户端所刊登的资料信息（诸如文字、图表、标识、按钮图标、图像、声音文件片段、数字下载、数据编辑和软件），均是儿医天使或其内容提供者的财产，受中国和国际版权法的保护。本客户端上所有内容的汇编是儿医天使的排他财产，受中国和国际版权法的保护。本客户端上所有软件都是儿医天使或其关联公司或其软件供应商的财产，受中国和国际版权法的保护。\n" +
                "第9条责任限制及不承诺担保\n" +
                "\uF0B7除非另有明确的书面说明，本客户端及其所包含的或以其它方式通过本客户端提供给您的全部信息、内容、材料、产品（包括软件）和服务，均是在“按现状”和“按现有”的基础上提供的。除非另有明确的书面说明，儿医天使不对本客户端的运营及其包含在本客户端上的信息、内容、材料、产品（包括软件）或服务作任何形式的、明示或默示的声明或担保（根据中华人民共和国法律另有规定的以外）。\n" +
                "\uF0B7儿医天使不担保本客户端所包含的或以其它方式通过本客户端提供给您的全部信息、内容、材料、产品（包括软件）和服务、其服务器或从本客户端发出的电子信件、信息没有病毒或其他有害成分。如因不可抗力或其它本站无法控制的原因使本客户端销售系统崩溃或无法正常使用导致网上交易无法完成或丢失有关的信息、记录等，儿医天使会合理地尽力协助处理善后事宜。\n" +
                "\uF0B7儿医天使所承载的内容（文、图、视频、音频）均为传播有益健康资讯目的，不对其真实性、科学性、严肃性做任何形式保证。\n" +
                "\uF0B7儿医天使拒绝回复医疗健康之外的咨询问题，包括但不限于如下情况：\n" +
                "  （1）非医疗健康类问题，如动物疾病问题、社会意识形态问题等；\n" +
                "  （2）医疗司法举证或询证问题；\n" +
                "  （3）胎儿性别鉴定问题；\n" +
                "  （4）未按提问要求提问，如提问时未指定医生，却要求具体医生回复；\n" +
                "  （5）有危害他人/自己可能的问题；\n" +
                "  （6）追问医生个人信息的问题；\n" +
                "  （7）故意挑逗、侮辱医生的提问。\n" +
                "\uF0B7儿医天使所有信息仅供参考，不做个别诊断、用药和使用的根据。\n" +
                "\uF0B7成都天使儿童医学研究院致力于提供正确、完整的健康资讯，但不保证信息的正确性和完整性，且不对因信息的不正确或遗漏导致的任何损失或损害承担责任。\n" +
                "\uF0B7儿医天使所提供的任何医疗信息，仅供参考，不能替代医生和其他医务人员的建议，如自行使用儿医天使中资料发生偏差，成都天使儿童医学研究院概不负责，亦不负任何法律责任。\n" +
                "\uF0B7《在线问诊》模块中的服务提供者均为拥有中华人民共和国医师资格证书的专业医生。但咨询建议仅为依据提问者描述而提供建议性内容，不能作为诊断及医疗的依据。\n" +
                "\uF0B7是否采用儿医天使的医生意见为提问者个人行为，成都天使儿童医学研究院不承担任何可能产生的责任。\n" +
                "\uF0B7成都天使儿童医学研究院保留对本声明作出不定时修改的权利。\n" +
                "第10条服务内容\n" +
                "10.1. 咨询服务条款\n" +
                "\uF0B7本条款所称“咨询”特指用户通过本客户端提交的咨询信息和通过预约特定医生的电话咨询，咨询的交互主体为本客户端的用户和儿医天使介绍的医生。\n" +
                "\uF0B7儿医天使需保证提供安全、稳定的服务场所，保证服务的顺利进行。\n" +
                "\uF0B7如服务在进行过程中由于儿医天使平台性能不稳定等系统原因导致服务不能完成的，将由儿医天使客服为您安排重新咨询服务。\n" +
                "\uF0B7用户必须在注册及申请收费服务时，详细阅读本客户端使用说明信息，并严格按要求操作。在个人信息部分必须提供真实的用户信息。\n" +
                "\uF0B7一旦发现用户提供的个人信息中有虚假，儿医天使有权立即终止向用户提供的所有服务，并冻结用户的帐户，有权要求用户赔偿因提供虚假信息给医生及儿医天使造成的损失。\n" +
                "\uF0B7咨询均只限于根据用户的主观描述，医生尽可能利用医学知识及临床经验给予一定的解惑及如何就医方面的建议，不保证满足用户要求的诊断、治疗方面的建议一定能获得。不对结果是否符合用户预期做保证。\n" +
                "\uF0B7咨询中医生所提供内容均是个人建议，不得做为诊断、治疗的直接医疗处置，用户需知晓并同意诊断及治疗均需前往医院。擅自将医生建议做为处方使用的，后果自负，与医生及儿医天使无关。\n" +
                "\uF0B7用户在咨询过程中要语言文明，尊重医生，平等交流。如有对医生恶意中伤，语言不文明，医生有权立即中断服务，用户无权要求退款。\n" +
                "\uF0B7电话咨询过程中遇到医生有紧急事情处理时（医生职业要求），要给予理解，暂停咨询，向儿医天使客服申报，以便安排重新咨询的具体时间。\n" +
                "10.2. 咨询服务中儿医天使与用户双方的权利及义务\n" +
                "\uF0B7儿医天使有义务在现有技术上维护平台服务的正常进行，并努力提升技术及改进技术，使网站服务更好进行。\n" +
                "\uF0B7儿医天使必须保证提供咨询服务的医生是本客户端介绍的医生本人提供咨询服务。\n" +
                "\uF0B7对于用户在本客户端预定服务中的不当行为或其它任何儿医天使认为应当终止服务的情况，儿医天使有权随时作出删除相关信息、终止服务提供等处理，而无须征得用户的同意。\n" +
                "\uF0B7如存在下列情况：\n" +
                "  （1）用户或其它第三方通知儿医天使，认为某个具体用户或具体交易事项可能存在重大问题；\n" +
                "  （2）用户或其它第三方向儿医天使告知咨询内容有违法或不当行为的，儿医天使以普通非专业的知识水平标准对相关内容进行判别，可以明显认为这些内容或行为具有违法或不当性质的。\n" +
                "\uF0B7儿医天使有义务对相关数据、所有的申请行为以及与咨询有关的其它事项进行审查。\n" +
                "\uF0B7儿医天使有权根据不同情况选择保留或删除相关信息或继续、停止对该用户提供服务，并追究相关法律责任。\n" +
                "\uF0B7咨询双方因服务引起的纠纷，请儿医天使给予调解的，儿医天使将有权了解相关信息，并将双方提供的信息与对方沟通。因在儿医天使上发生服务纠纷，引起诉讼的，用户通过司法部门或行政部门依照法定程序要求儿医天使提供相关数据，儿医天使应积极配合并提供有关资料。\n" +
                "\uF0B7儿医天使有权对用户的注册数据及电话咨询的行为进行查阅，发现注册数据或咨询行为中存在任何问题或怀疑，均有权向用户发出询问及要求改正的通知或者直接作出删除等处理。\n" +
                "\uF0B7当用户顺利使用完付费咨询服务后不得因为咨询服务中的内容不满意要求退款。\n" +
                "\uF0B7用户对咨询内容不满意，可以向儿医天使提出投诉，儿医天使有义务依据情况协调沟通，维护医生和用户关系和谐。\n" +
                "\uF0B7系统因下列状况无法正常运作，使用户无法使用电话咨询服务时，儿医天使不承担损害赔偿责任，该状况包括但不限于：\n" +
                "  （1）儿医天使在本网站公告之系统停机维护期间；\n" +
                "  （2）电信设备出现故障不能进行数据传输的；\n" +
                "  （3）因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成系统障碍不能执行业务的；\n" +
                "  （4）由于黑客攻击、电信部门技术调整或故障、银行方面的问题等原因而造成的服务中断或者延迟。\n" +
                "第11条协议更新及用户关注义务\n" +
                "根据国家法律法规变化及网络运营需要，儿医天使有权对本协议条款不时地进行修改，修改后的协议一旦被张贴在本客户端上即生效，并代替原来的协议。用户可随时登陆查阅最新协议；用户有义务不时关注并阅读最新版的协议及客户端公告。如用户不同意更新后的协议，可以且应立即停止接受儿医天使客户端依据本协议提供的服务；如用户继续使用本客户端提供的服务，即视为同意更新后的协议。儿医天使建议您在使用本客户端之前阅读本协议及本客户端的公告。如果本协议中任何一条被视为废止、无效或因任何理由不可执行，该条应视为可分的且并不影响任何其余条款的有效性和可执行性。\n" +
                "第12条法律管辖和适用\n" +
                "本协议的订立、执行和解释及争议的解决均应适用在中华人民共和国大陆地区适用之有效法律（但不包括其冲突法规则）。如发生本协议与适用之法律相抵触时，则这些条款将完全按法律规定重新解释，而其它有效条款继续有效。如缔约方就本协议内容或其执行发生任何争议，双方应尽力友好协商解决；协商不成时，任何一方均可向有管辖权的中华人民共和国大陆地区法院提起诉讼。\n" +
                "第13条其他\n" +
                "13.1. 儿医天使客户端所有者是指在政府部门依法许可或备案的儿医天使经营主体。\n" +
                "13.2. 儿医天使尊重用户和消费者的合法权利，本协议及本客户端上发布的各类规则、声明等其他内容，均是为了更好的、更加便利的为用户和消费者提供服务。本客户端欢迎用户和社会各界提出意见和建议，儿医天使将虚心接受并适时修改本协议及本客户端的各类规则。\n" +
                "13.3. 本协议内容中以黑体、加粗、下划线、斜体等方式显著标识的条款，请用户着重阅读。\n" +
                "13.4. 您点击本协议上方的“同意以下协议，提交”按钮即视为您完全接受本协议，在点击之前请您再次确认已知悉并完全理解本协议的全部内容。\n" +
                "成都天使儿童医学研究院\n" +
                "2016 年 12 月\n" +
                "\n" +
                "\n");
    }



    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}