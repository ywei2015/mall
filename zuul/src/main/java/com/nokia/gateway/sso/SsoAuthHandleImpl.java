package com.nokia.gateway.sso;

import com.bonc.sso.client.IAuthHandle;
import com.nokia.gateway.util.StringUtil;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SsoAuthHandleImpl implements IAuthHandle {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Override
	public boolean onSuccess(HttpServletRequest request, HttpServletResponse response, String loginId) {

		if ((request != null) && (loginId != null) && (loginId.trim().length() > 0)) {
			AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
			Map<String, Object> map = principal.getAttributes();
			
			/*logger.info("单点登录传递用户信息:->" + map.get("userId")+"-"+loginId+"-"+ StringUtil.deNull(map.get("userId"))+"-"+StringUtil.deNull(map.get("userName"))
					+"-"+StringUtil.deNull(map.get("sex"))+"-"+StringUtil.deNull(map.get("email"))+"-"+StringUtil.deNull(map.get("mobile"))
					+"-"+StringUtil.deNull(map.get("telephone"))+"-"+StringUtil.deNull(map.get("state"))+"-"+StringUtil.deNull(map.get("orgId"))+"-"+map.get("cityName"));*/
			logger.info("[{}] login",map.get("userId"));
			try {
				request.getSession().setAttribute("sso_user", loginId);
				request.getSession().setAttribute("userId", StringUtil.deNull(map.get("userId")));
				request.getSession().setAttribute("userName", StringUtil.deNull(map.get("userName")));
				request.getSession().setAttribute("sex", StringUtil.deNull(map.get("sex")));
				request.getSession().setAttribute("email", StringUtil.deNull(map.get("email")));
				request.getSession().setAttribute("mobile", StringUtil.deNull(map.get("mobile")));
				request.getSession().setAttribute("telephone", StringUtil.deNull(map.get("telephone")));
				request.getSession().setAttribute("state", StringUtil.deNull(map.get("state")));
				request.getSession().setAttribute("orgId", StringUtil.deNull(map.get("orgId")));
				String[] citys = StringUtil.deNull(map.get("cityName")).split("@");
				String[] cityNumbers = StringUtil.deNull(map.get("cityNumber")).split("@");
				String[] foreignNames = StringUtil.deNull(map.get("foreignName")).split("@");
				String[] coordinates = StringUtil.deNull(map.get("coordinate")).split("@");
				String[] left_bottom = null;
				if(map.get("left_bottom")!=null && !"".equals(map.get("left_bottom"))){
					left_bottom=StringUtil.deNull(map.get("left_bottom")).split("@");
				}
				
				String[] top_right = null;
				if(map.get("top_right")!=null && !"".equals(map.get("top_right"))){
					top_right=StringUtil.deNull(map.get("top_right")).split("@");
				}
				
				String[] data_source = null;
				if(map.get("data_source")!=null && !"".equals(map.get("data_source"))){
					data_source=StringUtil.deNull(map.get("data_source")).split("@");
				}
				List<String[]> ncs = new ArrayList<String[]>();
				if(citys != null && citys.length > 0){
					for (int i = 0; i < citys.length; i++) {
						//[AnYang', 安阳, 372, 114.34599,36.110584, , , ]
						String[] city = new String[7];
						city[0] = foreignNames[i];
						city[1] = citys[i];
						city[2] = cityNumbers[i];
						city[3] = coordinates[i];
						city[4] = "";
						if(data_source!=null && data_source.length>0){
							if(data_source[i]!=null && !"".equals(data_source[i])){
								city[4]=data_source[i];
							}
						}
						city[5] = "";
						if(left_bottom!=null && left_bottom.length>0){
							if(left_bottom[i]!=null && !"".equals(left_bottom[i])){
								city[5]=left_bottom[i];
							}
						}

						city[6] ="";
						if(top_right!=null && top_right.length>0){
							if(top_right[i]!=null && !"".equals(top_right[i])){
								city[6]=top_right[i];
							}
						}
						ncs.add(city);
						logger.info("单点登录传递用户地市信息:->" + foreignNames+"-"+citys[i]+"-"+cityNumbers[i]+"-"+coordinates[i]);
					}
				}
				//String[] c = StringTools.getCityByName(citys[citys.length-1]);
				//String[] ci = ncs.get(ncs.size()-1);
				request.getSession().setAttribute("citys", ncs);
//				request.getSession().setAttribute("city", StringTools.getCityByName(citys[citys.length-1]));
				if(ncs != null && ncs.size() > 0){
					request.getSession().setAttribute("city", ncs.get(ncs.size()-1));
				} else {
					request.getSession().setAttribute("city", null);
				}
				/*request.getSession().setAttribute("version", StringTools.getParamPropertiesVal("GLOBAL_VERSION"));*/
				request.getSession().setAttribute("styleName", "styleWhite");
			} catch (NumberFormatException e) {
				logger.info("单点登录方法报错！NumberFormatException:"+e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				logger.info("单点登录方法报错！Exception:"+e.getMessage());
				e.printStackTrace();
			}
		}else{
			logger.info("跳出单点登录："+request+"---loginId:"+loginId);
		}
		return true;
	}
}