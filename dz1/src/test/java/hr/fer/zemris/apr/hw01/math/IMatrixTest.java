package hr.fer.zemris.apr.hw01.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dbrcina
 */
public class IMatrixTest {

    private static final String RESOURCES_DIR = "src/test/resources/";

    private IMatrix a;
    private IMatrix b;
    private IMatrix c;

    @Before
    public void init() throws Exception {
        a = MatrixUtils.parseFromFile(RESOURCES_DIR + "a.txt");
        b = MatrixUtils.parseFromFile(RESOURCES_DIR + "b.txt");
        c = MatrixUtils.parseFromFile(RESOURCES_DIR + "c.txt");
    }

    @Test
    public void testAdd() throws Exception {
        IMatrix aAddBTxt = MatrixUtils.parseFromFile(RESOURCES_DIR + "a+b.txt");
        IMatrix aAddB = a.nAdd(b);
        Assert.assertEquals(aAddBTxt, aAddB);
    }

    @Test
    public void testSub() throws Exception {
        IMatrix aSubBTxt = MatrixUtils.parseFromFile(RESOURCES_DIR + "a-b.txt");
        IMatrix aSubB = a.nSub(b);
        Assert.assertEquals(aSubBTxt, aSubB);
    }

    @Test
    public void testMul() throws Exception {
        IMatrix aMulBTxt = MatrixUtils.parseFromFile(RESOURCES_DIR + "abT.txt");
        IMatrix aMulB = a.mul(b.transpose());
        Assert.assertEquals(aMulBTxt, aMulB);
    }

    @Test
    public void testScalar() throws Exception {
        IMatrix aScalar3Txt = MatrixUtils.parseFromFile(RESOURCES_DIR + "3.0a.txt");
        IMatrix aScalar3 = a.nScalarMul(3);
        Assert.assertEquals(aScalar3Txt, aScalar3);
    }

    @Test
    public void testDeterminant() {
        double classicDet = c.determinant();
        IMatrix[] results = MatrixUtils.luDecomposition(c.copy(), true);
        double lupDet = MatrixUtils.lupDeterminant(results[2].getNumOfRowsSwapped(), results[0], results[1]);
        Assert.assertEquals(classicDet, lupDet, MatrixUtils.DELTA);
    }

    @Test
    public void testInvert() {
        IMatrix classicInvert = c.invert();
        IMatrix lupInvert = MatrixUtils.lupInvert(c);
        Assert.assertEquals(classicInvert, lupInvert);
    }

}
