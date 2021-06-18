package marcian;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConservativeMartianTest {
    // Все тесты писал на инте потому, разницы особо не было а времени не хватало

    ConservativeMartian<Integer> conservativeMartian;
    InnovatorMartian<Integer> martian;

    @BeforeEach
    public void genLevel() {
        martian = new InnovatorMartian<Integer>(228, null);
        InnovatorMartianTest.genTree(martian, 6);
        conservativeMartian = new ConservativeMartian<>(martian);
    }


    @Test
    void getParent() {
        for (AbstractMartian<Integer> child:conservativeMartian.getChildren()) {
            Assert.assertEquals(conservativeMartian,child.getParent());
        }
    }

    @Test
    void getChildren() {
        var check = conservativeMartian.getChildren();
        for (int i = 0; i < martian.children.size(); i++) {
            Assert.assertEquals(conservativeMartian.children.get(i), check.get(i));
        }
    }

    @Test
    void getDescendants() {
        ArrayList<AbstractMartian<Integer>> descandants = new ArrayList<>();
        for (int i = 0; i < conservativeMartian.children.size(); i++) {
            var current = conservativeMartian.children.get(i);
            descandants.add(current);
            var children = current.getChildren();
            descandants.addAll(children);
        }
        var dest = conservativeMartian.getDescendants();
        for (AbstractMartian<Integer> desc : descandants) {
            Assert.assertTrue(dest.contains(desc));
        }
        Assert.assertEquals(dest.size(), descandants.size());
    }

    @Test
    void testToString() {
        System.out.println(conservativeMartian.toString(0));
        Assert.assertEquals("ConservativeMartian(Integer:228)", conservativeMartian.toString(0));
        Assert.assertEquals("        ConservativeMartian(Integer:228)", conservativeMartian.toString(2));
        Assert.assertEquals("            ConservativeMartian(Integer:228)", conservativeMartian.toString(3));
    }
}