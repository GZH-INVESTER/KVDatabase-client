package basic;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.model.KV;

/*����һ���������ݵĿͻ��ˣ������˿��ܻ�����
 * Ϊʲô��
 * ��Ϊ��Ҫ������������
 * ������Ϊʲô������
 * ��Ϊ�X�m
 * */

public class SocketClient {
	Integer method = 0, key, n = 0;
	String value = null;

	private void input() {
		Scanner scan = new Scanner(System.in);
		// �����������
		do {
			System.out.println("���������Ĳ�������,��������Ҫ��Ϊ����:\n1��ʾ����,2��ʾ����,3��ʾ���ݼ���ѯֵ,4��ʾ��ѯ����n��ļ�ֵ��");
			
			if (scan.hasNextInt()) {
				// �ж�������Ƿ�������
				method = scan.nextInt();
				if (method < 1 | method > 4) {
					System.out.println("����������Ƿ�");
					method = 0;
				}
			} else {
				// ����������Ϣ
				System.out.println("����Ĳ���������");
			}
		} while (method == 0);

		// ���������k
		if (method != 4) {
			System.out.println("�������");
			if (scan.hasNextInt()) {
				// �ж�������Ƿ�������
				key = scan.nextInt();
			} else {
				// ����������Ϣ
				System.out.println("����Ĳ���������");
			}
		} else {
			System.out.println("������nֵ");
			if (scan.hasNextInt()) {
				// �ж�������Ƿ�������
				n = scan.nextInt();
			} else {
				// ����������Ϣ
				System.out.println("����Ĳ���������");
			}
		}

		// ����ǲ���1��2��������ֵ
		if (method == 1 | method == 2) {
			System.out.println("���������Ӧ��ֵ���س���Ϊ������־:");
			if (scan.hasNext()) {
				value = scan.next();
			}
		}
		scan.close();
	}

	public static void main(String[] args) throws ClassNotFoundException {
		SocketClient sc = new SocketClient();
		sc.input();
		// 1.�����ͻ��˵�Socket��ָ����������IP�Ͷ˿�
		try {
			Socket socket = new Socket("127.0.0.1", 8080);

			// 2.��ȡ��Socket����������������л����������������Ϣ
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(new KV(sc.method, sc.key, sc.value, sc.n));
			socket.shutdownOutput();

			// 3.��ȡ��������ȡ�÷���������Ϣ
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String data = null;
			StringBuffer sb = new StringBuffer();
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
			//�����Ϣ
			System.out.println("�������˷��ص���Ϣ�ǣ�" + sb);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}