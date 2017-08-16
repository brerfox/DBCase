package services.dbService.dao;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import services.PropertyService;
import services.dbService.entities.*;
import services.dbService.executor.Executor;
import java.sql.Connection;
import services.dbService.DBService;

public class RequirementDAO {

	private Executor executor;
	
	public RequirementDAO(Connection connection){
		this.executor = new Executor(connection);
	}
	
	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
        PropertyService propertyService = PropertyService.getInstance();
        DBService dbService = new DBService(propertyService);
        RequirementDAO requirementDAO = new RequirementDAO(dbService.getConnection());
        requirementDAO.getEffectiveProfitLoss();

	}

	//requirement 1
    //TODO: change the query to remove the loop in java. Do this because otherwise what if we add more instruments??
    public List<AverageBuySell> getAverageBuyAndSell() throws SQLException {
        List<AverageBuySell> averagesList = new LinkedList();

        for(int i = 1001; i < 1013; ++i) {
            String getName = String.format("SELECT instrument_name from instrument where instrument_id = %d;", i);
            String s = (String)this.executor.execQuery(getName, (result) -> {
                return result.next() ? result.getString(1) : null;
            });
            String query = String.format("SELECT AVG(deal_amount) FROM deal where deal_instrument_id = %d and deal_type = 'B'", i);
            double buy = 0.0D;
            double sell = 0.0D;
            buy = ((Double)this.executor.execQuery(query, (result) -> {
                return result.next() ? result.getDouble(1) : null;
            })).doubleValue();
            buy = Math.floor(buy * 100.0D) / 100.0D;
            query = String.format("SELECT AVG(deal_amount) FROM deal where deal_instrument_id = %d and deal_type = 'S'", i);
            sell = ((Double)this.executor.execQuery(query, (result) -> {
                return result.next() ? result.getDouble(1) : null;
            })).doubleValue();
            sell = Math.floor(sell * 100.0D) / 100.0D;
            AverageBuySell abs = new AverageBuySell(s, (long)i, buy, sell);
            averagesList.add(abs);
        }

        Iterator var12 = averagesList.iterator();

        while(var12.hasNext()) {
            AverageBuySell a = (AverageBuySell)var12.next();
            System.out.println("name: " + a.getInstrumentName() + ", id: " + a.getInstrumentId() + ", average buy: " + a.getAverageBuy() + ", average sell: " + a.getAverageSell());
        }

        return averagesList;
    }

    //requirement 2
    public List<EndingPosition> getEndingPostions() throws SQLException {
        List<EndingPosition> epList = new LinkedList<>();
    	String s = "SELECT t.counterparty_name,t.instrument_name, buy-sell as ep from (select counterparty.counterparty_name,instrument.instrument_name, sum(case when deal_type = 'B' then deal_quantity end ) as buy, sum(case when deal_type = 'S' then deal_quantity end ) as sell from deal JOIN counterparty ON counterparty.counterparty_id = deal.deal_counterparty_id JOIN instrument ON instrument.instrument_id = deal.deal_instrument_id group by deal_counterparty_id, deal_instrument_id ) as t;";
    	epList = this.executor.execQuery(s, result -> {
    		List<EndingPosition> tmpList = new LinkedList<>();
    	    while(result.next()){
    			String counterparty_name = result.getString(1);
    			String instrument_name = result.getString(2);
    			long ep = result.getLong(3);
    			EndingPosition tmp = new EndingPosition(counterparty_name, instrument_name, ep);
    			tmpList.add(tmp);
        }
    		return tmpList;
    	});

        for(EndingPosition e : epList){
            System.out.println("counterparty name: "+e.getCounterpartyName()+", instrument name: "+e.getInstrumentName()+", ending position: "+e.getEndingPosition());
        }

    	return epList;
    }

    //requirement 3
    public List<RealizedProfitLoss> getRealizedProfitLoss() throws SQLException {
	    List<RealizedProfitLoss> epList = new LinkedList<>();

	    String s = "  select  t.counterparty_name,t.instrument_name,((buy_amount/total_buy_quantity)-(sell_amount/total_sell_quantity)) * total_sell_quantity as difference from (\n" +
                "\n" +
                "      select counterparty.counterparty_name,instrument.instrument_name,deal_counterparty_id,deal_instrument_id,\n" +
                "\t\t\t\t\t\t\tSum(case when deal_type='B' then deal_quantity end) as total_buy_quantity,\n" +
                "                            Sum(case when deal_type='S' then deal_quantity end) as total_sell_quantity,\n" +
                "                            Sum(case when deal_type='B' then deal_amount * deal_quantity end) as buy_amount,\n" +
                "                            Sum(case when deal_type='S' then deal_amount * deal_quantity end) as sell_amount\n" +
                "                            from deal\n" +
                "\t\tJOIN counterparty\n" +
                "         ON counterparty.counterparty_id = deal.deal_counterparty_id\n" +
                "        JOIN instrument\n" +
                "         ON instrument.instrument_id = deal.deal_instrument_id\n" +
                "\t\tGROUP BY deal_counterparty_id,deal_instrument_id)\n" +
                "as t;";

        epList = this.executor.execQuery(s, result -> {
            List<RealizedProfitLoss> tmpList = new LinkedList<>();
            while(result.next()){
                String counterparty_name = result.getString(1);
                String instrument_name = result.getString(2);
                double ep = result.getDouble(3);
                RealizedProfitLoss tmp = new RealizedProfitLoss(counterparty_name, instrument_name, ep);
                tmpList.add(tmp);
            }
            return tmpList;
        });

        for(RealizedProfitLoss e : epList){
            System.out.println("counterparty name: "+e.getCounterpartyName()+", instrument name: "+e.getInstrumentName()+", ending position: "+e.getRealized_profit());
        }

        return epList;

    }

    //requirement 4
    public List<EffectiveProfitLoss> getEffectiveProfitLoss() throws SQLException {

	    List<EffectiveProfitLoss> epList = new LinkedList<>();
        String s = "select counterparty_name,instrument_name,deal_counterparty_id,deal_instrument_id, ((buy_amount/total_buy_quantity)-(sell_amount/total_sell_quantity)) * total_sell_quantity as realized_profit, \n" +
                "ABS(total_buy_quantity-total_sell_quantity) as difference_quantity\n" +
                "         from (\n" +
                "\n" +
                "select counterparty_name,instrument_name,deal_instrument_id,deal_counterparty_id,\n" +
                "           \n" +
                "\t\t\tSum(case when deal_type='B' then deal_quantity end) as total_buy_quantity,\n" +
                "\t\t\tSum(case when deal_type='S' then deal_quantity end) as total_sell_quantity,\n" +
                "\t\t\tSum(case when deal_type='B' then deal_amount * deal_quantity end) as buy_amount,\n" +
                "\t\t\tSum(case when deal_type='S' then deal_amount * deal_quantity end) as sell_amount\n" +
                "\t\t    from deal \n" +
                "            JOIN counterparty\n" +
                "        ON counterparty.counterparty_id = deal.deal_counterparty_id\n" +
                "    JOIN instrument\n" +
                "        ON instrument.instrument_id = deal.deal_instrument_id\n" +
                "GROUP BY deal_counterparty_id,deal_instrument_id) as t;";

        //ToDo: here we will do the query and make objects for buy sell for each instrument
        epList = this.executor.execQuery(s, result -> {
            List<LatestBuySellTimes> times = new LinkedList<>();

            String s1 = "select deal_instrument_id,\n" +
                    "            Max(case when deal_type='B' then deal_time end) as end_buy_time,\n" +
                    "            Max(case when deal_type='S' then deal_time end) as end_sell_time\n" +
                    "\t\t    from deal\n" +
                    "GROUP BY deal_instrument_id;";
            times = this.executor.execQuery(s1, resultSet -> {
                List<LatestBuySellTimes> tmp = new LinkedList<>();
                while(resultSet.next()){
                    long deal_instrument_id = resultSet.getLong(1);
                    String end_buy_time = resultSet.getString(2);
                    String end_sell_time = resultSet.getString(3);
                    LatestBuySellTimes l = new LatestBuySellTimes(deal_instrument_id, end_buy_time, end_sell_time);
                    tmp.add(l);
                }
                return tmp;
            });

            List<EffectiveProfitLoss> tmpList = new LinkedList<>();
            while(result.next()){

                double effectivePrice = 0;

                //assuming we get counterparty name as index 1
                String counterparty_name = result.getString(1);
                long deal_instrument_id = result.getLong(4);
                //assuming we get instrument name as index 2
                String instrument_name = result.getString(2);

                //assuming we get realized profit as index 5
                double realizedProfit = result.getLong(5);

                //assuming we get ending position as index 6
                long endpos = result.getLong(6);

                //TODO: get latest buy time and sell time buy using object from last ToDo and doing where instrument_id = this things instrument_id
                String latest_buy_time = "";
                String latest_sell_time = "";

                for(LatestBuySellTimes l : times){
                   if(l.getDeal_instrument_id() == deal_instrument_id){
                       latest_buy_time = l.getEnd_buy_time();
                       latest_sell_time = l.getEnd_sell_time();
                       break;
                   }
                }

                if(endpos < 0){
                    //we have more sells than buys

                    //TODO: get latest sell based on time
                    String amount = String.format("select deal_amount from deal where deal_time = %d and deal_instrument_id = %d;", latest_sell_time, deal_instrument_id);
                    double sell = this.executor.execQuery(amount, resultSet -> {
                        return resultSet.getDouble(1);
                    }); //select deal_amount from deal where deal_time = '2017-07-28T18:06:29.861' and deal_instrument_id = 1003;

                    double soldEndPos = sell*endpos;

                    //pretty sure we subtract here because we are looking to buy stuff so our profit goes down???
                    effectivePrice = realizedProfit + soldEndPos;



                } else {
                    //we have more buys than sells

                    //TODO: get latest buy based on time
                    String amount = String.format("select deal_amount from deal where deal_time = '%s' and deal_instrument_id = %d;", latest_sell_time, deal_instrument_id);
                    double buy = this.executor.execQuery(amount, resultSet -> {
                        resultSet.next();

                        return resultSet.getDouble(1);

                    }); //select deal_amount from deal where deal_time = '2017-07-28T18:06:29.861' and deal_instrument_id = 1003;

                    //select deal_amount from deal where deal_time = '2017-07-28T18:06:29.861' and deal_instrument_id = 1003;

                    double buyEndPos = buy*endpos;

                    //Pretty sure we add here because we are selling to buyers so increase profit???
                    effectivePrice = realizedProfit + buyEndPos;

                }
                EffectiveProfitLoss tmp = new EffectiveProfitLoss(counterparty_name, instrument_name, effectivePrice);
                tmpList.add(tmp);
            }
            return tmpList;
        });

        double sum = 0;
        for(EffectiveProfitLoss e : epList){

            if(e.getCounterpartyName().equals("Lewis")) {
                sum += e.getEffective_profit();
            } else {
                System.out.println(sum);
            }
            System.out.println("counterparty name: "+e.getCounterpartyName()+", instrument name: "+e.getInstrumentName()+", ending position: "+e.getEffective_profit());


        }

	    return epList;
    }

}
