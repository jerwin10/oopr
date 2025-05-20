// Source code is decompiled from a .class file using FernFlower decompiler.
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

class LoginRegistrationSystem$RoundedBorder extends AbstractBorder {
   private int radius;

   LoginRegistrationSystem$RoundedBorder(int var1) {
      this.radius = var1;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      Graphics2D var7 = (Graphics2D)var2.create();
      var7.setColor(new Color(255, 255, 255, 100));
      var7.setStroke(new BasicStroke(2.0F));
      var7.drawRoundRect(var3 + 1, var4 + 1, var5 - 3, var6 - 3, this.radius, this.radius);
      var7.dispose();
   }

   public Insets getBorderInsets(Component var1) {
      return new Insets(this.radius / 2, this.radius / 2, this.radius / 2, this.radius / 2);
   }

   public Insets getBorderInsets(Component var1, Insets var2) {
      var2.left = var2.top = var2.right = var2.bottom = this.radius / 2;
      return var2;
   }
}
