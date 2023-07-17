package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Sale> list = new ArrayList<>();

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.next();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			if (path == null) {
				throw new IOException("O sistema não pode encontrar o arquivo especificado");
			}
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				int items = Integer.parseInt(fields[3]);
				double total = Double.parseDouble(fields[4]);
				list.add(new Sale(month, year, seller, items, total));
				line = br.readLine();
			}
			
			System.out.println();
			System.out.println("Total de vendas por vendedor:");
			Set<String> names = new HashSet<>();
			
			for(Sale sale : list) {
				names.add(sale.getSeller());
			}
			
			Map<String, Double> sellerTotalSale = new HashMap<>();
			for(String name : names) {
					double amount = list.stream()
							.filter(p -> p.getSeller().equals(name))
							.map(p -> p.getTotal())
							.reduce(0.0, (x,y) -> x + y);
					sellerTotalSale.put(name, amount);	
			}
			
			for(String key : sellerTotalSale.keySet()) {
				System.out.println(key + " - R$ " + String.format("%.2f", sellerTotalSale.get(key)));
			}
		} 
		catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

}
