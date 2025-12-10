UPDATE Backend_servicecategory SET category_url = '/api/v1/mobile/list-bio-waste-categories' WHERE id = 1;
UPDATE Backend_servicecategory SET category_url = '/api/v1/mobile/list-scrap-categories' WHERE id = 2;

UPDATE Backend_servicecategory SET order_url = '/api/v1/order/place-bio-waste-order' WHERE id = 1;
UPDATE Backend_servicecategory SET order_url = '/api/v1/order/place-scrap-order' WHERE id = 2;
