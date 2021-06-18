package marcian;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MarcianTreeTest {
    // Все тесты писал на инте потому, разницы особо не было а времени не хватало

    static Random rnd = new Random();

    void genTree(InnovatorMartian<Integer> parent, int count) {
        for (int i = 0; i < count; i++) {
            var current = new InnovatorMartian<>(rnd.nextInt(1000), parent);
            parent.addChild(current);
            for (int j = 0; j < count / 2; j++) {
                current.addChild(new InnovatorMartian<>(rnd.nextInt(1000), current));
            }
        }
    }

    @Test
    void report() {

        var root = new InnovatorMartian<Integer>(0, null);
        genTree(root, 6);
        MarcianTree tree = new MarcianTree(root);
        System.out.println(tree.report());
    }

    @Test
    void getMartianFromReport() {
        var root = new InnovatorMartian<Integer>(0, null);
        genTree(root, 6);
        MarcianTree tree = new MarcianTree(root);

        var ntree = MarcianTree.GetTreFromReport(tree.report());
        Assert.assertEquals(ntree.report(), tree.report());
    }
}