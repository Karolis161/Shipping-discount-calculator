import com.asseco.praktika.dto.Prices;
import com.asseco.praktika.dto.Transactions;
import com.asseco.praktika.service.DiscountCalculator;
import com.asseco.praktika.utils.ArgumentsParser;
import com.asseco.praktika.utils.InputProcessor;
import com.asseco.praktika.utils.OutputProcessor;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ArgumentsParser argumentsParser = new ArgumentsParser(args);
        InputProcessor inputProcessor = new InputProcessor();
        OutputProcessor outputProcessor = new OutputProcessor();
        DiscountCalculator discountCalculator = new DiscountCalculator();

        String inputFileName = argumentsParser.getInputFileName();
        String outputFileName = argumentsParser.getOutputFileName();
        String outputFileType = argumentsParser.getOutputFileType();

        List<Transactions> shippingTransactions = inputProcessor.getShippingTransactions();
        List<Prices> shippingPrices = discountCalculator.getShippingPrices();

        inputProcessor.readValues(inputFileName);
        discountCalculator.calculateDiscount(shippingTransactions);
        outputProcessor.writeValues(shippingTransactions, shippingPrices, outputFileName, outputFileType);
    }
}