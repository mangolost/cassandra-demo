CREATE KEYSPACE db_order WITH REPLICATION = {'class':'SimpleStrategy','replication_factor':1};

CREATE TABLE t_order_data (
    mobile text,
    create_date bigint,
    order_no text,
    shop_id text,
    total_amount double,
    PRIMARY KEY (mobile, create_date, order_no)
) WITH
  CLUSTERING ORDER BY (create_date DESC,order_no DESC)
  AND comment='会员购买订单记录表'
  AND default_time_to_live=7776000;

CREATE TABLE t_order_data (
    mobile text,
    create_date bigint,
    order_no text,
    shop_id text,
    total_amount double,
    PRIMARY KEY (mobile, create_date, order_no)
) WITH
  CLUSTERING ORDER BY (create_date DESC,order_no DESC)
  AND comment='会员购买订单记录表'
  AND read_repair_chance=0
  AND dclocal_read_repair_chance=0.1
  AND gc_grace_seconds=864000
  AND bloom_filter_fp_chance=0.01
  AND compaction={ 'class':'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy',
  'max_threshold':'32',
  'min_threshold':'4' }
  AND compression={ 'chunk_length_in_kb':'64',
  'class':'org.apache.cassandra.io.compress.LZ4Compressor' }
  AND caching={ 'keys':'ALL',
  'rows_per_partition':'NONE' }
  AND default_time_to_live=7776000
  AND id='08dc8720-cfb2-11ea-8deb-4b4bd57c4710'
  AND min_index_interval=128
  AND max_index_interval=2048
  AND memtable_flush_period_in_ms=0
  AND speculative_retry='99PERCENTILE';

