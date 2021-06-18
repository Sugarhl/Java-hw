package marcian;

public class MarcianTree {
    AbstractMartian root;

    /**
     * Конструктор дерева по корню
     * @param root
     */
    public MarcianTree(AbstractMartian root) {
        this.root = root;
    }

    /**
     * Метод получения отчета
     * @return Строка-отчет
     */
    public String report() {
        return prepareReport(root, 0);
    }

    /**
     * Подготовка отчета
     * @param current Текущий корень
     * @param level Уровень сдвига
     * @return Строка-отчет
     */
    private String prepareReport(AbstractMartian current, int level) {
        StringBuilder result = new StringBuilder(current.toString(level) + "\n");
        for (int i = 0; i < current.children.size(); i++) {
            result.append(prepareReport((AbstractMartian) current.children.get(i), level + 1));
        }
        return result.toString();
    }

    /**
     * Метод построения дерева по отчету
     * @param report Отчет
     * @return Дерево
     */
    public static MarcianTree GetTreFromReport(String report) {
        System.out.println(report);
        var martians = report.split("\n");
        System.out.println(martians[1]);

        String description = martians[0].substring(martians[0].lastIndexOf('(') + 1);
        description = description.substring(0, description.length() - 1);
        AbstractMartian root = AbstractMartian.Parse(null,
                description);
        AbstractMartian parent = root;
        int parlevel = 0;

        for (int i = 1; i < martians.length; i++) {
            var level = martians[i].lastIndexOf(" ");
            description = martians[i].substring(martians[i].lastIndexOf('(') + 1);
            description = description.substring(0, description.length() - 1);
            if (level == parlevel && parent != null) {
                parent = parent.parent;
            }
            var current = AbstractMartian.Parse(parent, description);
            if (parent != null)
                ((InnovatorMartian) parent).addChild(current);
            if (i != martians.length - 1 && level < martians[i + 1].lastIndexOf(" ")) {
                parent = current;
                parlevel = level;
            }
        }
        if (martians[0].startsWith("ConservativeMartian")) {
            root = new ConservativeMartian((InnovatorMartian) root);
        }
        return new MarcianTree(root);
    }
}
