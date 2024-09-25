import java.util.Arrays;
import java.io.*;

public class Polynomial 
{
    public double[] coefficients;
    public int[] exponents;

    // Constructor 1
    public Polynomial()
    {
        this.coefficients = null;
        this.exponents = null;
    }

    // Constructor 2
    public Polynomial(double[] coefficients_array, int[] exponents_array)
    {
        if(coefficients_array.length == 0)
        {
            this.coefficients = null;
            this.exponents = null;
        }
        else
        {
            this.coefficients = new double[coefficients_array.length];
            this.exponents = new int[exponents_array.length];
            for(int i = 0; i < coefficients_array.length; i++)
            {
                if(coefficients_array[i] != 0)
                {
                    this.coefficients[i] = coefficients_array[i];
                    this.exponents[i] = exponents_array[i];
                }
            }
        }
    }

    // Constructor 3
    public Polynomial(File file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        reader.close();

        String[] terms;
        terms = line.split("(?=[+-])");
        
        double[] parsed_coefficients = new double[terms.length];
        int[] parsed_exponents = new int[terms.length];
        
        String term;

        for (int i = 0; i < terms.length; i++)
        {
            term = terms[i];
            int index = term.indexOf('x');

            if(index != -1)
            {
                parsed_coefficients[i] = Double.parseDouble(term.substring(0, index));
                parsed_exponents[i] = Integer.parseInt(term.substring(index + 1));
            }
            else
            {
                parsed_coefficients[i] = Double.parseDouble(term);
                parsed_exponents[i] = 0;
            }
        }

        this.coefficients = parsed_coefficients;
        this.exponents = parsed_exponents;
    }

    public Polynomial add(Polynomial p)
    {
        if(this.coefficients == null)
        {
            return p;
        }
        if(p.coefficients == null)
        {
            return this;
        }

        // Initialize the result arrays with total length of both arrays
        int max_length = highestExp(p) + 1;

        double[] result_coefficients = new double[max_length];
        int[] result_exponents = new int[max_length];
        int index;

       for (int i = 0; i < this.coefficients.length; i++)
       {
            index = this.exponents[i];
            result_coefficients[index] += this.coefficients[i];
            result_exponents[index] = this.exponents[i];
       }

       for (int i = 0; i < p.coefficients.length; i++)
       {
            index = p.exponents[i];
            result_coefficients[index] += p.coefficients[i];
            result_exponents[index] = p.exponents[i];
       }

        //Removing zero coefficients and resize arrary

        return trimmedPolynomial(result_coefficients, result_exponents, max_length);
    }

    public double evaluate(double x)
    {
        if(this.coefficients == null)
        {
            return 0;
        }

        double result = 0;
        
        for (int i = 0; i < coefficients.length; i++)
        {
            result += coefficients[i]*(Math.pow(x,this.exponents[i]));
        }
        return result;
    }

    public boolean hasRoot(double num)
    {
        if(this.coefficients == null)
        {
            return false;
        }
        return this.evaluate(num) == 0;
    }
    

    public Polynomial multiply(Polynomial p)
    {
        if(this.coefficients == null || p.coefficients == null)
        {
            return new Polynomial();
        }
        // Find max length for result array
        int max_length = highestExp(p) + 1;

        // Initialize result array with max length
        double[] result_coefficients = new double[max_length];
        int[] result_exponents = new int[max_length];

        for (int i = 0; i < this.exponents.length; i++)
        {
            for (int j = 0; j < p.exponents.length; j++)
            {
                int new_exp = this.exponents[i] + p.exponents[j];
                result_coefficients[new_exp] += (this.coefficients[i]*p.coefficients[j]);
                result_exponents[new_exp] = new_exp;
            }
        }

        return trimmedPolynomial(result_coefficients, result_exponents, max_length);
    }

    // Helper function to find max length
    public int highestExp(Polynomial p)
    {
        int highest_exp_1 = 0;
        int highest_exp_2 = 0;

        for (int i = 0; i < this.exponents.length; i++)
        {
            if(this.exponents[i] > highest_exp_1)
            {
                highest_exp_1 = this.exponents[i];
            }
        }

        for (int i = 0; i < p.exponents.length; i++)
        {
            if(p.exponents[i] > highest_exp_2)
            {
                highest_exp_2 = p.exponents[i];
            }
        }

        return highest_exp_1 + highest_exp_2; 
    }

    public Polynomial trimmedPolynomial(double[] coefficients, int[] exponents, int length)
    {
        int final_length = 0;

        double[] final_coefficients = new double[length];
        int[] final_exponents = new int[length];

        for(int i = 0; i < length; i++)
        {
            if(coefficients[i] != 0)
            {
                final_coefficients[final_length] = coefficients[i];
                final_exponents[final_length] = exponents[i];
                final_length++;
            }
        }

        final_coefficients = Arrays.copyOf(final_coefficients, final_length);
        final_exponents = Arrays.copyOf(final_exponents, final_length);

        return new Polynomial(final_coefficients, final_exponents);
    }

    public void saveToFile(String filename) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for(int i = 0; i < this.coefficients.length; i++)
        {
            if (this.coefficients[i] != 0)
            {
                if(coefficients[i] > 0 && i != 0)
                {
                    writer.write("+");
                }
                writer.write(Double.toString(this.coefficients[i]));

                if (this.exponents[i] !=0)
                {
                    writer.write("x");
                    if(this.exponents[i] != 1)
                    {
                        writer.write(Integer.toString(this.exponents[i]));
                    }
                }
            }
        }

        writer.close();
    }

}