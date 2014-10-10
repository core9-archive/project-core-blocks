/**
 *  Copyright 2013 Wordnik, Inc.
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

package io.core9.editor.resource;

import io.core9.editor.data.BlockRegistryData;
import io.core9.editor.exception.NotFoundException;
import io.core9.editor.model.BlockRegistryItem;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;


@Path("/store")
@Api(value="/store" , description = "Operations about store")
@Produces({"application/json", "application/xml"})
public class BlockRegistryResource {
  static BlockRegistryData storeData = new BlockRegistryData();
  static JavaRestResourceUtil ru = new JavaRestResourceUtil();

  @GET
  @Path("/order/{orderId}")
  @ApiOperation(value = "Find purchase order by ID",
    notes = "For valid response try integer IDs with value <= 5 or > 10. Other values will generated exceptions",
    response = BlockRegistryItem.class)
  @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied"),
      @ApiResponse(code = 404, message = "BlockRegistryItem not found") })
  public Response getBlockRegistryItemById(
      @ApiParam(value = "ID of pet that needs to be fetched", allowableValues = "range[1,5]", required = true) @PathParam("orderId") String orderId)
      throws NotFoundException {
    BlockRegistryItem order = storeData.findBlockRegistryItemById(ru.getLong(0, 10000, 0, orderId));
    if (null != order) {
      return Response.ok().entity(order).build();
    } else {
      throw new NotFoundException(404, "BlockRegistryItem not found");
    }
  }

  @POST
  @Path("/order")
  @ApiOperation(value = "Place an order for a pet",
    response = BlockRegistryItem.class)
  @ApiResponses({ @ApiResponse(code = 400, message = "Invalid BlockRegistryItem") })
  public Response placeBlockRegistryItem(
      @ApiParam(value = "order placed for purchasing the pet", required = true) BlockRegistryItem order) {
    storeData.placeBlockRegistryItem(order);
    return Response.ok().entity("").build();
  }

  @DELETE
  @Path("/order/{orderId}")
  @ApiOperation(value = "Delete purchase order by ID",
    notes = "For valid response try integer IDs with value < 1000. Anything above 1000 or nonintegers will generate API errors")
  @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied"),
      @ApiResponse(code = 404, message = "BlockRegistryItem not found") })
  public Response deleteBlockRegistryItem(
      @ApiParam(value = "ID of the order that needs to be deleted", allowableValues = "range[1,infinity]", required = true) @PathParam("orderId") String orderId) {
    storeData.deleteBlockRegistryItem(ru.getLong(0, 10000, 0, orderId));
    return Response.ok().entity("").build();
  }
}
