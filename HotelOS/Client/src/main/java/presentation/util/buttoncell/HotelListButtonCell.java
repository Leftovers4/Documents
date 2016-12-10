package presentation.util.buttoncell;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import presentation.hotelworkerui.hotelworkerscene.OrderDetailPane;
import presentation.hotelworkerui.hotelworkerscene.UpdateOrderInfoPane;
import util.OrderType;
import vo.order.OrderVO;

/**
 * Created by Hitiger on 2016/11/30.
 * Description :
 */
public class HotelListButtonCell extends TableCell<OrderVO, Boolean> {

    private TableView tableView;
    private Pane mainPane;

    public HotelListButtonCell(final Pane mainPane, final TableView tableView) {
        this.mainPane = mainPane;
        this.tableView = tableView;
        this.getStylesheets().add(HotelListButtonCell.class.getResource("/css/hotelworker/hotelworkerstyle.css").toExternalForm());
    }

    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            HBox btnBox = new HBox();
            Button detailButton = new Button();

            Image detailImage = new Image("/img/hotelworker/checkorderdetail.png");
            detailButton.setGraphic(new ImageView(detailImage));
            detailButton.getStyleClass().add("TableButtonCell");

            detailButton.setOnAction(event -> {
                int selectedIndex = getTableRow().getIndex();
                OrderVO orderVO = (OrderVO) tableView.getItems().get(selectedIndex);
                mainPane.getChildren().clear();
                mainPane.getChildren().add(new OrderDetailPane(mainPane, false,true,orderVO));
            });

            btnBox.setSpacing(10);
            btnBox.setAlignment(Pos.CENTER);
            btnBox.getChildren().clear();

            OrderVO temp = (OrderVO)tableView.getItems().get(getTableRow().getIndex());

            if(temp.orderType == OrderType.Executed){
                if(temp.orderTimeVO.leaveTime != null){
                    btnBox.setAlignment(Pos.CENTER_LEFT);
                    btnBox.setPadding(new Insets(0,0,0,13));
                    btnBox.getChildren().add(detailButton);
                }else {
                    Button checkOutButton = new Button();

                    Image checkOutImage = new Image("/img/hotelworker/checkout.png");
                    checkOutButton.setGraphic(new ImageView(checkOutImage));
                    checkOutButton.getStyleClass().add("TableButtonCell");

                    checkOutButton.setOnAction(event -> {
                        int selectedIndex = getTableRow().getIndex();
                        OrderVO orderVO = (OrderVO) tableView.getItems().get(selectedIndex);
                        mainPane.getChildren().clear();
                        mainPane.getChildren().add(new UpdateOrderInfoPane(mainPane, false, true, orderVO));
                    });

                    btnBox.setPadding(new Insets(0,5,0,20));
                    btnBox.getChildren().addAll(detailButton, checkOutButton);
                }
            }else {
                if(temp.orderType == OrderType.Canceled){
                    btnBox.setAlignment(Pos.CENTER_LEFT);
                    btnBox.setPadding(new Insets(0,0,0,13));
                    btnBox.getChildren().add(detailButton);
                }else {
                    Button checkInButton = new Button();

                    Image checkInImage = new Image("/img/hotelworker/checkin.png");
                    checkInButton.setGraphic(new ImageView(checkInImage));
                    checkInButton.getStyleClass().add("TableButtonCell");

                    checkInButton.setOnAction(event -> {
                        int selectedIndex = getTableRow().getIndex();
                        OrderVO orderVO = (OrderVO) tableView.getItems().get(selectedIndex);
                        mainPane.getChildren().clear();
                        mainPane.getChildren().add(new UpdateOrderInfoPane(mainPane, true, true, orderVO));
                    });

                    btnBox.setPadding(new Insets(0,5,0,20));
                    btnBox.getChildren().addAll(detailButton, checkInButton);
                }
            }

            setGraphic(btnBox);
            setText(null);
        }
    }
}