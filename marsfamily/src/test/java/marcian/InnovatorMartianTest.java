package marcian;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class InnovatorMartianTest {
    // Все тесты писал на инте потому, разницы особо не было а времени не хватало

    static Random rnd = new Random();

    InnovatorMartian<Integer> martian;

    public static void genTree(InnovatorMartian<Integer> parent, int count) {
        for (int i = 0; i < count; i++) {
            var current = new InnovatorMartian<>(rnd.nextInt(1000), parent);
            parent.addChild(current);
            for (int j = 0; j < count / 2; j++) {
                current.addChild(new InnovatorMartian<>(rnd.nextInt(1000), current));
            }

        }
    }

    @BeforeEach
    public void genLevel() {
        martian = new InnovatorMartian<Integer>(228, null);
        genTree(martian, 6);
    }

    @Test
    void setGeneticCode() {

        for (int i = 0; i < 10; i++) {
            var test = rnd.nextInt();
            martian.setGeneticCode(test);
            Assert.assertEquals((Integer) test, martian.geneticСode);
        }
    }

    @Test
    void getParent() {
        for (int i = 0; i < martian.children.size(); i++) {
            Assert.assertEquals(martian, martian.children.get(i).getParent());
        }
    }

    @Test
    void setParent() {
        var parent = new InnovatorMartian<Integer>(37, null);
        Assert.assertTrue(martian.setParent(parent));
        Assert.assertEquals(parent, martian.getParent());
        var child = martian.children.get(rnd.nextInt(martian.children.size()));
        var descadant = child.children.get(rnd.nextInt(child.children.size()));
        Assert.assertFalse(martian.setParent((InnovatorMartian<Integer>) child));
        Assert.assertFalse(martian.setParent((InnovatorMartian<Integer>) descadant));
    }

    @Test
    void getChildren() {
        var check = martian.getChildren();
        for (int i = 0; i < martian.children.size(); i++) {
            Assert.assertEquals(martian.children.get(i), check.get(i));
        }
    }

    @Test
    void setChildren() {
        ArrayList<AbstractMartian<Integer>> newchildren = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var child = new InnovatorMartian<>(rnd.nextInt(), null);
            newchildren.add(child);
        }
        var child = martian.children.get(rnd.nextInt(martian.children.size()));
        InnovatorMartian<Integer> descadant = (InnovatorMartian<Integer>) child.children.get(rnd.nextInt(child.children.size()));
        Assert.assertFalse(descadant.setChildren(martian.children));
        Assert.assertTrue(martian.setChildren(newchildren));
    }

    @Test
    void getDescendants() {
        ArrayList<AbstractMartian<Integer>> descandants = new ArrayList<>();
        for (int i = 0; i < martian.children.size(); i++) {
            var current = martian.children.get(i);
            descandants.add(current);
            descandants.addAll(current.children);
        }
        var dest = martian.getDescendants();
        for (AbstractMartian<Integer> desc : descandants) {
            Assert.assertTrue(dest.contains(desc));
        }
        Assert.assertEquals(dest.size(), descandants.size());
    }

    @Test
    void addChild() {
        var nchild = new InnovatorMartian<Integer>(232, null);
        var child = martian.children.get(rnd.nextInt(martian.children.size()));
        var descadant = child.children.get(rnd.nextInt(child.children.size()));
        Assert.assertFalse(martian.addChild((InnovatorMartian<Integer>) child));
        Assert.assertFalse(martian.addChild((InnovatorMartian<Integer>) descadant));
        Assert.assertTrue(martian.addChild(nchild));
    }

    @Test
    void removeChild() {
        var nchild = new InnovatorMartian<Integer>(232, null);
        Assert.assertFalse(martian.removeChild(nchild));
        Assert.assertTrue(martian.removeChild((InnovatorMartian<Integer>)
                martian.children.get(rnd.nextInt(martian.children.size()))));
    }

    @Test
    void testToString() {
        Assert.assertEquals("InnovatorMartian(Integer:228)", martian.toString(0));
        Assert.assertEquals("        InnovatorMartian(Integer:228)", martian.toString(2));
        Assert.assertEquals("            InnovatorMartian(Integer:228)", martian.toString(3));

    }
}