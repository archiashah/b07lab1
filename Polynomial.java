public class Polynomial 
{
    public double[] coefficients;

    public Polynomial()
    {
        coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients_array)
    {
        coefficients = coefficients_array;
    }

    public Polynomial add(Polynomial p)
    {
        int max_length = Math.max(p.coefficients.length, coefficients.length);
        double[] result_array = new double[max_length];
        Polynomial result = new Polynomial(result_array);
        double a = 0;
        double b = 0;

        for (int i = 0; i < max_length; i++)
        {
            a = 0;
            b = 0;
            if(i < coefficients.length)
            {
                a = coefficients[i];
            }
            if(i < p.coefficients.length)
            {
                b = p.coefficients[i];
            }

            result.coefficients[i] = a + b;
        }

        return result;
    }

    public double evaluate(double x)
    {

        int length = coefficients.length;
        double result = 0;
        
        for (int i = 0; i < length; i++)
        {
            result = result + coefficients[i]*(Math.pow(x,i));
        }
        return result;
    }

    public boolean hasRoot(double num)
    {
        return evaluate(num) == 0;
    }
    

}