/**
 *  Copyright 2012 Wordnik, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.core9.editor.data;

import io.core9.editor.model.BlockRegistryItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BlockRegistryData {
	static List<BlockRegistryItem> orders = new ArrayList<BlockRegistryItem>();

	static {
		orders.add(createBlockRegistryItem(1, 1, 2, new Date(), "placed"));
		orders.add(createBlockRegistryItem(2, 1, 2, new Date(), "delivered"));
		orders.add(createBlockRegistryItem(3, 2, 2, new Date(), "placed"));
		orders.add(createBlockRegistryItem(4, 2, 2, new Date(), "delivered"));
		orders.add(createBlockRegistryItem(5, 3, 2, new Date(), "placed"));
		orders.add(createBlockRegistryItem(11, 3, 2, new Date(), "placed"));
		orders.add(createBlockRegistryItem(12, 3, 2, new Date(), "placed"));
		orders.add(createBlockRegistryItem(13, 3, 2, new Date(), "placed"));
		orders.add(createBlockRegistryItem(14, 3, 2, new Date(), "placed"));
		orders.add(createBlockRegistryItem(15, 3, 2, new Date(), "placed"));
	}

	public BlockRegistryItem findBlockRegistryItemById(long orderId) {
		for (BlockRegistryItem order : orders) {
			if (order.getId() == orderId) {
				return order;
			}
		}
		return null;
	}

	public void placeBlockRegistryItem(BlockRegistryItem order) {
		if (orders.size() > 0) {
			for (int i = orders.size() - 1; i >= 0; i--) {
				if (orders.get(i).getId() == order.getId()) {
					orders.remove(i);
				}
			}
		}
		orders.add(order);
	}

	public void deleteBlockRegistryItem(long orderId) {
		if (orders.size() > 0) {
			for (int i = orders.size() - 1; i >= 0; i--) {
				if (orders.get(i).getId() == orderId) {
					orders.remove(i);
				}
			}
		}
	}

	private static BlockRegistryItem createBlockRegistryItem(long id, long petId, int quantity,
			Date shipDate, String status) {
		BlockRegistryItem order = new BlockRegistryItem();
		order.setId(id);
		order.setPetId(petId);
		order.setQuantity(quantity);
		order.setShipDate(shipDate);
		order.setStatus(status);
		return order;
	}
}