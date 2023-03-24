package com.megazone.core.redis;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author redis,
 */
public interface Redis {
	//key

	/**
	 * @param keys keys
	 */
	public void del(Object... keys);

	/**
	 * @param key key
	 * @return time
	 */
	public Long ttl(String key);

	/**
	 * @param key     key
	 * @param timeout
	 */
	public void expire(String key, Integer timeout);


	/**
	 * @param key key
	 */
	public void persist(String key);

	/**
	 * @param key key
	 */
	public boolean exists(String key);

	/**
	 * @param key key
	 * @return DataType
	 */
	public DataType getType(String key);


	/**
	 * set
	 *
	 * @param key   key
	 * @param value value
	 */
	public void set(String key, Object value);

	/**
	 * set
	 *
	 * @param key    key
	 * @param value  value
	 * @param second
	 */
	public void setex(Object key, Integer second, Object value);

	/**
	 * @param key     key
	 * @param timeout ，
	 * @param value   value
	 * @return boolean true，
	 */
	public Boolean setNx(String key, Long timeout, Object value);


	/**
	 * @param key key
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key);

	/**
	 * @param keys keys
	 * @return values
	 */
	public List<Object> mGet(Object... keys);

	/**
	 * @param map map
	 */
	public void mSet(Map<String, Object> map);

	/**
	 * @param map map
	 */
	public void mSetNx(Map<String, Object> map);


	/**
	 * @param key   key
	 * @param value
	 */
	public void appendStr(Object key, String value);


	/**
	 * @param key      key
	 * @param hashKeys hashKeys
	 */
	public void hdel(Object key, Object... hashKeys);

	/**
	 * @param key     key
	 * @param hashKey mapkey
	 * @param value   mapvalue
	 */
	public void put(Object key, Object hashKey, Object value);


	/**
	 * @param key key
	 * @param map map
	 */
	public void putAll(String key, Map<Object, Object> map);

	/**
	 * @param key key
	 * @return map
	 */
	public Map<Object, Object> getRedisMap(String key);

	/**
	 * @param key key
	 * @return value
	 */
	public List<Object> getValues(Object key);

	/**
	 * @param key     key
	 * @param hashKey hashKey
	 */
	public Boolean hashMapKey(Object key, String hashKey);

	/**
	 * @param key   key
	 * @param value value
	 */
	public void lpush(String key, Object value);

	/**
	 * @param key   key
	 * @param value value
	 */
	public void rpush(String key, Object value);


	/**
	 * @param key key
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	public <T> T lPop(String key);

	/**
	 * @param key key
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	public <T> T rPop(String key);


	/**
	 * @param key   key
	 * @param index index
	 * @return value
	 */

	public <T> T getKeyIndex(String key, int index);

	/**
	 * @param key key
	 * @return length
	 */
	public Long getLength(String key);

	/**
	 * @param key   key
	 * @param start
	 * @param end
	 * @return values
	 */
	public List<Object> range(String key, int start, int end);


	//set

	/**
	 * @param key    key
	 * @param values values
	 */
	public void addSet(String key, Object... values);

	/**
	 * @param key key
	 * @return value
	 */

	public <T> T getSet(String key);

	/**
	 * @param key key
	 * @return values
	 */
	public Set<Object> getSets(String key);


	/**
	 * @param key key
	 * @return length
	 */
	public Long getSetsNum(String key);

	/**
	 * @param key key
	 * @return values
	 */
	public Set<Object> members(String key);


	//zSet

	/**
	 * <p>
	 * <p>
	 * 1.set
	 * Set<ZSetOperations.TypedTuple<Object>> sets=new HashSet<>();
	 * 2.
	 * ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>(value,，);
	 * 4.set
	 * sets.add(objectTypedTuple1);
	 * 5.
	 * reidsImpl.Zadd("zSet", list);
	 *
	 * @param key    key
	 * @param tuples tuples
	 */
	public void zadd(String key, Set<ZSetOperations.TypedTuple<Object>> tuples);


	/**
	 * @param key key
	 * @param min min
	 * @param max max
	 * @return values
	 */
	public Set<Object> reverseRange(String key, Double min, Double max);

}
