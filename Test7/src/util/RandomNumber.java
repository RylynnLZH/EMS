package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import bean.ValidatedCode;

public class RandomNumber {

	// 随机生成4个数字 并生成图片 相应到客户端
	public ValidatedCode generateImage() throws IOException {
		String sRand = "";
		Random random = new Random();

		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}

		// 创建图像
		int width = 80, heigth = 30;
		BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);

		// 获取图像上下文
		Graphics g = image.getGraphics();

		// 设定背景色
		g.setColor(getRandColor(100, 250));

		// 画一个矩形
		g.fillRect(0, 0, width, heigth);

		// 设定字体
		g.setFont(new Font("Timess New Random", Font.PLAIN, 28));

		// 随机产生155干扰线
		g.setColor(getRandColor(160, 200));

		for (int i = 0; i < 255; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(heigth);
			int x1 = random.nextInt(12);
			int y1 = random.nextInt(12);
			g.drawLine(x, y, x + x1, y + y1);
		}

		// 设置颜色
		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

		// 将验证码显示到图像中
		g.drawString(sRand, 10, 26);

		// 绘图工具释放
		g.dispose();

		ValidatedCode vc = new ValidatedCode();
		vc.setImage(image);
		vc.setRand(sRand);

		return vc;

	}

	Color getRandColor(int fc, int bc) {

		// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);

		return new Color(r, g, b);
	}

}
