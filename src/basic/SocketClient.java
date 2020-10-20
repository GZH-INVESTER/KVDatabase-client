package basic;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.model.KV;

/*这是一个极其生草的客户端，你用了可能会骂人
 * 为什么呢
 * 因为他要反复运行输入
 * 但是我为什么不改呢
 * 因为蒟蒻
 * */

public class SocketClient {
	Integer method = 0, key, n = 0;
	String value = null;

	private void input() {
		Scanner scan = new Scanner(System.in);
		// 输入操作类型
		do {
			System.out.println("请输入您的操作类型,输入类型要求为整数:\n1表示插入,2表示更新,3表示根据键查询值,4表示查询量第n大的键值对");
			
			if (scan.hasNextInt()) {
				// 判断输入的是否是整数
				method = scan.nextInt();
				if (method < 1 | method > 4) {
					System.out.println("输入的整数非法");
					method = 0;
				}
			} else {
				// 输入错误的信息
				System.out.println("输入的不是整数！");
			}
		} while (method == 0);

		// 输入键或者k
		if (method != 4) {
			System.out.println("请输入键");
			if (scan.hasNextInt()) {
				// 判断输入的是否是整数
				key = scan.nextInt();
			} else {
				// 输入错误的信息
				System.out.println("输入的不是整数！");
			}
		} else {
			System.out.println("请输入n值");
			if (scan.hasNextInt()) {
				// 判断输入的是否是整数
				n = scan.nextInt();
			} else {
				// 输入错误的信息
				System.out.println("输入的不是整数！");
			}
		}

		// 如果是操作1，2，则输入值
		if (method == 1 | method == 2) {
			System.out.println("请输入键对应的值，回车作为结束标志:");
			if (scan.hasNext()) {
				value = scan.next();
			}
		}
		scan.close();
	}

	public static void main(String[] args) throws ClassNotFoundException {
		SocketClient sc = new SocketClient();
		sc.input();
		// 1.创建客户端的Socket，指定服务器的IP和端口
		try {
			Socket socket = new Socket("127.0.0.1", 8080);

			// 2.获取该Socket的输出流，对象序列化，向服务器发送信息
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(new KV(sc.method, sc.key, sc.value, sc.n));
			socket.shutdownOutput();

			// 3.获取输入流，取得服务器的信息
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String data = null;
			StringBuffer sb = new StringBuffer();
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
			//输出信息
			System.out.println("服务器端返回的信息是：" + sb);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}