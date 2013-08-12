import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 学习参考网页：Servlet 3.0 新特性详解
 * href="http://www.ibm.com/developerworks/cn/java/j-lo-servlet30
 * 
 * @author wangguozheng
 *
 */

@WebServlet(
		urlPatterns="/gip",
		initParams = {@WebInitParam(name = "P1", value = "123"),
				@WebInitParam(name = "P2", value = "234"),
				@WebInitParam(name = "P3", value = "345"),
				@WebInitParam(name = "P4", value = "456"),
		}
)
public class GetInitParamServlet extends HttpServlet {
	private static final Logger logger=LoggerFactory.getLogger(GetInitParamServlet.class);

	private static final long serialVersionUID = -2128122335811219481L;

	private HashMap<String, String> initParamsMap = new HashMap<String, String>();//一个存放初始参数的集合

	public void init() throws ServletException {

		//1. 获得初始参数集合
		Enumeration<String> initParams = getServletConfig().getInitParameterNames();
		logger.debug("获得了Servelet的initParam初始参数"+initParams);

		//2. 循环所有的初始参数
		while (initParams.hasMoreElements()) {
			//2.1 获得初始参数名称
			String initParamName = initParams.nextElement();
			//2.2 获得初始参数值
			String initParamValue = getServletConfig().getInitParameter(initParamName);
			logger.debug("参数名称:[ "+initParamName+" ]   参数值:[ "+initParamValue+" ]");
			//2.3 把所有的初始参数放到一个HashMap里面
			initParamsMap.put(initParamName, initParamValue);
		}

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

		PrintWriter out = res.getWriter();
		res.setCharacterEncoding("text/plain");//以一般文本的形式返回，不是网页
		Iterator<Map.Entry<String, String>> iter = initParamsMap.entrySet().iterator();
		
		//3. 通过Interator循环所有的初始参数，并反馈给客户参数的信息
		while (iter.hasNext()) {

			Map.Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue();

			out.write("Param:[ "+key+" ]   value:[ "+value+" ]\n");

		}

		out.close();

	}

}
